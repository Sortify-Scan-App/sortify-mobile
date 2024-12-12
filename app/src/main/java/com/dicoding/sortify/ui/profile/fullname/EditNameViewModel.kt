package com.dicoding.sortify.ui.profile.fullname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.data.repository.AuthRepository
import kotlinx.coroutines.launch

class EditNameViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> get() = _updateResult

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun updateProfile(token: String, name: String) {
        viewModelScope.launch {
            try {
                val result = repository.updateFullName(token, name)
                _updateResult.postValue(result.message == "Full name updated successfully")
            } catch (e: Exception) {
                _updateResult.postValue(false)
            }
        }
    }
}
