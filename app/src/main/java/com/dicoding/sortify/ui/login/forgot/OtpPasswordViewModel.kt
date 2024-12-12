package com.dicoding.sortify.ui.login.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.sortify.data.remote.response.OtpResponse
import com.dicoding.sortify.data.remote.response.VerifyOtpResponse
import com.dicoding.sortify.data.repository.AuthRepository
import com.dicoding.sortify.helper.Result

class OtpPasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun verifyOtpPassword(email: String, otp: String): LiveData<Result<VerifyOtpResponse>> {
        _loading.value = true
        val result = authRepository.verifyOtpPassword(email, otp)
        result.observeForever {
            _loading.value = it is Result.Loading
        }
        return result
    }
}