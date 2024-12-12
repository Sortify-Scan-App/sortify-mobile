package com.dicoding.sortify.ui.login.forgot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.sortify.data.pref.ResetPasswordRequest
import com.dicoding.sortify.data.remote.retrofit.ApiConfig
import com.dicoding.sortify.data.remote.retrofit.ApiService
import com.dicoding.sortify.databinding.ActivityChangePasswordBinding
import com.dicoding.sortify.databinding.ActivityResetPasswordBinding
import com.dicoding.sortify.ui.login.LoginActivity
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding

    private var userEmail: String = ""
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userEmail = intent.getStringExtra(EXTRA_EMAIL) ?: ""
        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Email tidak ditemukan. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupApiService()

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnRpassword.setOnClickListener {
            validatePasswords()
        }
    }

    private fun setupApiService() {
        apiService = ApiConfig.getAuthService()
    }

    private fun validatePasswords() {
        val newPassword = binding.edResetPassword.text.toString().trim()
        val confirmPassword = binding.edConfirmPassword.text.toString().trim()

        when {
            newPassword.isEmpty() || confirmPassword.isEmpty() -> {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            newPassword != confirmPassword -> {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            }
            else -> {
                resetPassword(newPassword, confirmPassword)
            }
        }
    }

    private fun resetPassword(newPassword: String, confirmPassword: String) {
        lifecycleScope.launch {
            try {
                val request = ResetPasswordRequest(
                    email = userEmail,
                    newPassword = newPassword,
                    confirmPassword = confirmPassword
                )
                val response = apiService.resetPassword(request)
                if (!response.message.isNullOrEmpty()) {
                    Toast.makeText(this@ResetPasswordActivity, response.message, Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } else {
                    Toast.makeText(this@ResetPasswordActivity, "Terjadi kesalahan, silakan coba lagi.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ResetPasswordActivity, "Gagal mengganti password: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }
}
