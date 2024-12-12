package com.dicoding.sortify.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.data.pref.ResetPasswordRequest
import com.dicoding.sortify.data.remote.retrofit.ApiConfig
import com.dicoding.sortify.data.remote.retrofit.ApiService
import com.dicoding.sortify.databinding.ActivityChangePasswordBinding
import com.dicoding.sortify.ui.home.HomeViewModel
import kotlinx.coroutines.launch

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var apiService: ApiService
    private val homeViewModel: HomeViewModel by viewModels {
        AuthViewModelFactory.getInstance(this)
    }
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupApiService()

        observeUserSession()

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.changePasswordBtn.setOnClickListener {
            validatePasswords()
        }
    }

    private fun setupApiService() {
        apiService = ApiConfig.getAuthService()
    }

    private fun observeUserSession() {
        homeViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                userEmail = user.email
            } else {
                Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validatePasswords() {
        val newPassword = binding.edEditPassword.text.toString().trim()
        val confirmPassword = binding.edConfirmPassword.text.toString().trim()

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (newPassword != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
        } else {
            resetPassword(newPassword, confirmPassword)
        }
    }

    private fun resetPassword(newPassword: String, confirmPassword: String) {
        if (userEmail.isNullOrEmpty()) {
            Toast.makeText(this, "Email not found. Please try again.", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val request = ResetPasswordRequest(
                    email = userEmail!!,
                    newPassword = newPassword,
                    confirmPassword = confirmPassword
                )
                val response = apiService.resetPassword(request)
                if (!response.message.isNullOrEmpty()) {
                    Toast.makeText(this@ChangePasswordActivity, response.message, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@ChangePasswordActivity, "An error occurred, please try again.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ChangePasswordActivity, "Failed to change password: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
