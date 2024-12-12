package com.dicoding.sortify.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.data.repository.AuthRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
