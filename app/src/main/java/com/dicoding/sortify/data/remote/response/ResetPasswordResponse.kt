package com.dicoding.sortify.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(

	@field:SerializedName("message")
	val message: String? = null
)
