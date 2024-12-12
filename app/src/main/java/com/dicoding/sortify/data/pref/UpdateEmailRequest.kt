package com.dicoding.sortify.data.pref


data class UpdateEmailRequest(
    val token: String,
    val newEmail: String
)