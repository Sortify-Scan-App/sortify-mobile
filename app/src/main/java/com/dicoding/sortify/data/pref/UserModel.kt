package com.dicoding.sortify.data.pref

data class UserModel(
    val fullname: String,
    val email: String,
    val token: String?,
    val isLogin: Boolean,
)
