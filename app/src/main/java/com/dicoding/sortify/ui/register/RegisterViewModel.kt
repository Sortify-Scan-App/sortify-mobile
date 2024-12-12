package com.dicoding.sortify.ui.register

import androidx.lifecycle.ViewModel
import com.dicoding.sortify.data.repository.AuthRepository

class RegisterViewModel(private val repository: AuthRepository): ViewModel() {
    fun register(fullname: String, email: String, password: String) = repository.register(fullname, email, password)
}