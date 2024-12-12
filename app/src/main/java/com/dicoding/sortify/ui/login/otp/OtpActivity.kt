package com.dicoding.sortify.ui.login.otp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.data.remote.response.OtpResponse
import com.dicoding.sortify.databinding.ActivityOtpBinding
import com.dicoding.sortify.helper.Result
import com.dicoding.sortify.ui.home.HomeActivity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private lateinit var otpViewModel: OtpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        otpViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory.getInstance(this)
        )[OtpViewModel::class.java]

        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnVerifyOtp.setOnClickListener {
            verifyOtpProcess()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun verifyOtpProcess() {
        val otp = binding.edOtp.text.toString().trim()

        if (otp.isEmpty()) {
            showToast("Masukkan OTP terlebih dahulu")
            return
        }

        lifecycleScope.launch {
            try {
                val user = otpViewModel.getSession().firstOrNull()
                val email = user?.email ?: ""
                if (email.isNotEmpty()) {
                    verifyOtp(email, otp)
                } else {
                    showToast("Email tidak ditemukan")
                }
            } catch (e: Exception) {
                Log.e("OtpActivity", "Error getting session", e)
                showToast("Terjadi kesalahan saat memproses")
            }
        }
    }

    private fun verifyOtp(email: String, otp: String) {
        otpViewModel.verifyOtp(email, otp).observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    handleSuccessfulOtpVerification(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    handleOtpVerificationError(result.error)
                }
            }
        }
    }

    private fun handleSuccessfulOtpVerification(otpResponse: OtpResponse) {
        if (otpResponse.token != null) {
            val fullname = otpResponse.user?.fullname ?: ""
            val userModel = UserModel(
                fullname = otpResponse.user?.fullname ?: "",
                email = otpResponse.user?.email ?: "",
                token = otpResponse.token,
                isLogin = true
            )

            lifecycleScope.launch {
                otpViewModel.saveSession(userModel)
                showToast("OTP successfully verified. Welcome! $fullname")
                navigateToHome()
            }
        } else {
            showToast(otpResponse.message ?: "OTP Verification Failed")
        }
    }

    private fun handleOtpVerificationError(errorMessage: String) {
        Log.e("OtpActivity", "OTP Verification Error: $errorMessage")
        showToast(errorMessage)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            lottieLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnVerifyOtp.isEnabled = !isLoading
            edOtp.isEnabled = !isLoading
        }
    }

    private fun setupObservers() {
        otpViewModel.loading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }
}
