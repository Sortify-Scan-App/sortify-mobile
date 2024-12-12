package com.dicoding.sortify.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.sortify.data.repository.AuthRepository
import com.dicoding.sortify.di.Injection
import com.dicoding.sortify.ui.home.HomeViewModel
import com.dicoding.sortify.ui.login.LoginViewModel
import com.dicoding.sortify.ui.login.forgot.ForgotPasswordViewModel
import com.dicoding.sortify.ui.login.forgot.OtpPasswordViewModel
import com.dicoding.sortify.ui.login.otp.OtpViewModel
import com.dicoding.sortify.ui.profile.email.EditEmailViewModel
import com.dicoding.sortify.ui.profile.fullname.EditNameViewModel
import com.dicoding.sortify.ui.profile.ProfileViewModel
import com.dicoding.sortify.ui.register.RegisterViewModel

class AuthViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(OtpViewModel::class.java) -> {
                OtpViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(OtpPasswordViewModel::class.java) -> {
                OtpPasswordViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->{
                HomeViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->{
                ProfileViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(EditNameViewModel::class.java) ->{
                EditNameViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(EditEmailViewModel::class.java) ->{
                EditEmailViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) ->{
                ForgotPasswordViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        fun getInstance(context: Context) = AuthViewModelFactory(Injection.provideRepository(context))
    }
}
