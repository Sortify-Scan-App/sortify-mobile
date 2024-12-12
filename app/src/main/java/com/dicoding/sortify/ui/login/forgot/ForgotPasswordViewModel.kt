package com.dicoding.sortify.ui.login.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.sortify.data.repository.AuthRepository
import com.dicoding.sortify.helper.Result

class ForgotPasswordViewModel(private val repository: AuthRepository) : ViewModel() {

    fun requestOtp(email: String): LiveData<Result<String>> {
        return repository.requestOtp(email)
    }
}
