package com.dicoding.sortify.ui.profile.fullname

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.databinding.ActivityEditNameBinding
import kotlinx.coroutines.launch

class EditNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNameBinding
    private val viewModel: EditNameViewModel by viewModels {
        AuthViewModelFactory.getInstance(this)
    }
    private var userToken: String? = null
    private var userFullName: String? = null
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNameBinding.inflate(layoutInflater)
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
                        fullname = binding.edEditName.text.toString().trim(),
                        email = userEmail?: "",
                        token = userToken,
                        isLogin = true,
                    )
                )
                Toast.makeText(this, "Name updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeUserSession() {
        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                userToken = user.token
                userFullName = user.fullname
                userEmail = user.email

                binding.edEditName.setText(userFullName)
            } else {
                Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validateAndUpdateProfile() {
        val name = binding.edEditName.text.toString().trim()

        binding.nameEditTextLayout.error = null

        val nameChanged = name != userFullName

        if (nameChanged) {

            userToken?.let {
                updateProfile(it, name)
            } ?: run {
                Toast.makeText(this, "User token is missing. Please log in again.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No changes to update", Toast.LENGTH_SHORT).show()
        }
    }




    private fun updateProfile(token: String, name: String) {
        lifecycleScope.launch {
            viewModel.updateProfile(token, name)
        }
    }
}