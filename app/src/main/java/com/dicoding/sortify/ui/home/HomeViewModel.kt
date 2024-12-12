package com.dicoding.sortify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.data.repository.AuthRepository

class HomeViewModel (private val repository: AuthRepository) : ViewModel()  {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}