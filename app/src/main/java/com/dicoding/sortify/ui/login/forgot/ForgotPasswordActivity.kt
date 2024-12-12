package com.dicoding.sortify.ui.login.forgot

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.databinding.ActivityForgotPasswordBinding
import com.dicoding.sortify.helper.Result
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = AuthViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ForgotPasswordViewModel::class.java]

        setupAction()
    }

    private fun setupAction() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.continueButton.setOnClickListener {
            val email = binding.edForgotPassEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                MainScope().launch {
                    viewModel.requestOtp(email).observe(this@ForgotPasswordActivity) { result ->
                        when (result) {
                            is Result.Loading -> showLoading(true)
                            is Result.Success -> {
                                showLoading(false)
                                showToast("OTP has been sent to your email.")
                                navigateToOtp(email)
                            }
                            is Result.Error -> {
                                showLoading(false)
                                showToast(result.error)
                            }
                        }
                    }
                }
            } else {
                showToast("Please enter a valid email")
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.lottieLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun navigateToOtp(email: String) {
        val intent = Intent(this, OtpPasswordActivity::class.java).apply {
            putExtra(OtpPasswordActivity.EXTRA_EMAIL, email)
        }
        startActivity(intent)
        finish()
    }


}


