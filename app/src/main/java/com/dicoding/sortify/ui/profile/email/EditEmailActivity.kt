package com.dicoding.sortify.ui.profile.email

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.databinding.ActivityEditEmailBinding
import kotlinx.coroutines.launch

class EditEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditEmailBinding
    private val viewModel: EditEmailViewModel by viewModels {
        AuthViewModelFactory.getInstance(this)
    }
    private var userToken: String? = null
    private var userFullName: String? = null
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeUserSession()

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.eprofileBtn.setOnClickListener {
            validateAndUpdateProfile()
        }

        viewModel.updateResult.observe(this) { isSuccess ->
            if (isSuccess) {
                viewModel.saveSession(
                    UserModel(
                        fullname = userFullName?:"",
                        email = binding.edEditEmail.text.toString().trim(),
                        token = userToken,
                        isLogin = true,
                    )
                )
                Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeUserSession() {
        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                userToken = user.token
                userFullName = user.fullname
                userEmail = user.email

                binding.edEditEmail.setText(userEmail)
            } else {
                Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validateAndUpdateProfile() {
        val email = binding.edEditEmail.text.toString().trim()
        binding.emailEditTextLayout.error = null

        val emailChanged = email != userEmail

        if (emailChanged) {

            userToken?.let {
                updateEmail(it, email)
            } ?: run {
                Toast.makeText(this, "User token is missing. Please log in again.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No changes to update", Toast.LENGTH_SHORT).show()
        }
    }




    private fun updateEmail(token: String, email: String) {
        lifecycleScope.launch {
            viewModel.updateEmail(token, email)
        }
    }
}