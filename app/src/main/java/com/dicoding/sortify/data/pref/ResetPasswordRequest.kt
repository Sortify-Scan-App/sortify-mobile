package com.dicoding.sortify.data.pref

data class ResetPasswordRequest(
    val email: String,
    val newPassword: String,
    val confirmPassword: String
)
