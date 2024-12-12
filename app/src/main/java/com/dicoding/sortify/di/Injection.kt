package com.dicoding.sortify.di

import android.content.Context
import com.dicoding.sortify.data.pref.UserPreference
import com.dicoding.sortify.data.remote.retrofit.ApiConfig
import com.dicoding.sortify.data.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context)
        runBlocking { pref.getSession().first() }
        ApiConfig.getAuthService()
        return AuthRepository.getInstance(pref)
    }
}