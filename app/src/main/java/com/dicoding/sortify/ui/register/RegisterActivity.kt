package com.dicoding.sortify.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.sortify.R
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.databinding.ActivityRegisterBinding
import com.dicoding.sortify.databinding.SuccessAuthDialogBinding
import com.dicoding.sortify.helper.Result
import com.dicoding.sortify.ui.login.LoginActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = AuthViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        setupAction()
    }

    private fun setupAction() {
        binding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.registerButton.setOnClickListener {
            val username = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()

            MainScope().launch {
                viewModel.register(username, email, password).observe(this@RegisterActivity) { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            showSuccessDialog()
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }
                    }
                }
            }
        }
    }

    private fun showSuccessDialog() {
        val bindingDialog = SuccessAuthDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this).apply {
            setView(bindingDialog.root)
            setCancelable(false)
        }.create().apply {
            val dialog = this
            bindingDialog.tvDialogTitle.text = getString(R.string.register_successful)
            bindingDialog.tvDialogMessage.text = getString(R.string.register_message)

            bindingDialog.btnDialogContinue.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                dialog.dismiss()
            }
        }.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.lottieLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
