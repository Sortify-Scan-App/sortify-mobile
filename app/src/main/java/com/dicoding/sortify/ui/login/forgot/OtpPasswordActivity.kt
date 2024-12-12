package com.dicoding.sortify.ui.login.forgot

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.data.remote.response.VerifyOtpResponse
import com.dicoding.sortify.databinding.ActivityOtpPasswordBinding
import com.dicoding.sortify.helper.Result
import com.dicoding.sortify.ui.login.LoginActivity
import kotlinx.coroutines.launch

class OtpPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpPasswordBinding
    private lateinit var viewModel: OtpPasswordViewModel
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        email = intent.getStringExtra(EXTRA_EMAIL) ?: ""
        println("Received email: $email")

        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory.getInstance(this)
        )[OtpPasswordViewModel::class.java]

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
            showToast("Please enter OTP")
            return
        }

        lifecycleScope.launch {
            verifyOtpPassword(email, otp)
        }
    }

    private fun verifyOtpPassword(email: String, otp: String) {
        viewModel.verifyOtpPassword(email, otp).observe(this) { result ->
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

    private fun handleSuccessfulOtpVerification(otpResponse: VerifyOtpResponse) {
        if (otpResponse.message == "OTP verified successfully. You can now reset your password.") {
            showToast("OTP successfully verified! You can now reset your password.")

            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.putExtra(ResetPasswordActivity.EXTRA_EMAIL, email)
            startActivity(intent)
            finish()
        } else {
            showToast(otpResponse.message ?: "OTP Verification Failed")
        }
    }


    private fun handleOtpVerificationError(errorMessage: String) {
        showToast(errorMessage)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            lottieLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnVerifyOtp.isEnabled = !isLoading
            edOtp.isEnabled = !isLoading
        }
    }
    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }
}
