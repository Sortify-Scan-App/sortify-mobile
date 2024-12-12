package com.dicoding.sortify.data.remote.response

import com.google.gson.annotations.SerializedName

data class OtpResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class User(

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
