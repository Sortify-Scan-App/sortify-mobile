//package com.dicoding.sortify.di
//
//import android.content.Context
//import kotlinx.coroutines.runBlocking
//
//object Injection {
//    fun provideRepository(context: Context): ARepository {
//        val pref = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { pref.getSession().first() }
//        val apiService = ApiConfig.getApiService(user.token)
//        val database = StoryDatabase.getDatabase(context)
//        return UserRepository.getInstance(apiService, pref, database)
//    }
//}