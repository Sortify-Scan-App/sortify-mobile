package com.dicoding.sortify.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.sortify.R
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.databinding.ActivityLoginBinding
import com.dicoding.sortify.databinding.SuccessAuthDialogBinding
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.helper.Result
import com.dicoding.sortify.ui.login.forgot.ForgotPasswordActivity
import com.dicoding.sortify.ui.home.HomeActivity
import com.dicoding.sortify.ui.login.otp.OtpActivity
import com.dicoding.sortify.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    private var isDialogShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, AuthViewModelFactory.getInstance(this)
        )[LoginViewModel::class.java]

        setupView()
        setupActions()
    }

    private fun setupView() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setupActions() {
        binding.registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString().trim()
            val password = binding.edLoginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.error_empty_credentials))
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewModel.login(email, password).observe(this@LoginActivity) { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)

                        is Result.Success -> {
                            val loginResult = result.data
                            viewModel.saveSession(
                                UserModel(
                                    fullname = loginResult.fullname ?: "",
                                    email = email,
                                    token = null,
                                    isLogin = true,
                                )
                            )
                            showSuccessDialog()
                            showLoading(false)
                        }

                        is Result.Error -> {
                            val errorMessage = getString(R.string.error_invalid_credentials)
                            showToast(errorMessage)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showSuccessDialog() {
        val bindingDialog = SuccessAuthDialogBinding.inflate(layoutInflater)
        isDialogShown = true
        val dialog = AlertDialog.Builder(this).apply {
            setView(bindingDialog.root)
            setCancelable(false)
        }.create()

        bindingDialog.btnDialogContinue.setOnClickListener {
            navigateToOtp()
            isDialogShown = false
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToOtp() {
        val intent = Intent(this, OtpActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.lottieLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
