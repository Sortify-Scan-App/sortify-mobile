package com.dicoding.sortify.ui.scan.camera

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.dicoding.sortify.R
import com.dicoding.sortify.databinding.ActivityCameraBinding
import com.dicoding.sortify.helper.createCustomTempFile

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private var flashEnabled: Boolean = false
    private var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
            startCamera()
        }

        binding.btnFlash.setOnClickListener {
            if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                flashEnabled = !flashEnabled
                toggleTorch(flashEnabled)
                updateFlashIcon()
            } else {
                Toast.makeText(this, "Flash hanya tersedia untuk kamera belakang", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCapture.setOnClickListener { takePhoto() }
    }

    private fun toggleTorch(enableTorch: Boolean) {
        camera?.cameraControl?.enableTorch(enableTorch)?.addListener({
            runOnUiThread {
                try {
                    updateFlashIcon()
                    if (enableTorch) {
                        Toast.makeText(this, "Flash dinyalakan", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Gagal mengubah status torch: ${e.message}")
                }
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun updateFlashIcon() {
        binding.btnFlash.setImageResource(
            if (flashEnabled) R.drawable.ic_flash_on else R.drawable.ic_flash_off
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            try {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                // Buat Preview
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                    }

                // Konfigurasi ImageCapture
                imageCapture = ImageCapture.Builder()
                    .setTargetRotation(binding.root.display.rotation)
                    .build()

                // Reset state flash jika pindah kamera
                if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                    flashEnabled = false
                    updateFlashIcon()
                }

                try {
                    // Unbind semua use case sebelum rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases ke camera
                    camera = cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageCapture
                    )

                    // Set initial torch state
                    if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                        toggleTorch(flashEnabled)
                    }

                } catch (exc: Exception) {
                    Log.e(TAG, "Gagal mengikat use case: ${exc.message}")
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal memunculkan kamera.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (exc: Exception) {
                Log.e(TAG, "Gagal memulai kamera: ${exc.message}")
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra(EXTRA_CAMERAX_IMAGE, output.savedUri.toString())
                    setResult(CAMERAX_RESULT, intent)
                    finish()
                }

                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Gagal mengambil gambar: ${exc.message}")
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private val orientationEventListener by lazy {
        object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageCapture?.targetRotation = rotation
            }
        }
    }

    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
    }
}