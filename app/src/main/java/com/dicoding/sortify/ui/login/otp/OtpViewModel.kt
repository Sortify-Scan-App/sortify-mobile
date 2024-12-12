package com.dicoding.sortify.ui.login.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.data.remote.response.OtpResponse
import com.dicoding.sortify.data.repository.AuthRepository
import com.dicoding.sortify.helper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OtpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getSession(): Flow<UserModel?> {
        return authRepository.getSession()
    }
    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            authRepository.saveSession(userModel)
        }
    }

    fun verifyOtp(email: String, otp: String): LiveData<Result<OtpResponse>> {
        _loading.value = true
        val result = authRepository.verifyOtp(email, otp)
        result.observeForever {
            _loading.value = it is Result.Loading
        }
        return result
    }
}
