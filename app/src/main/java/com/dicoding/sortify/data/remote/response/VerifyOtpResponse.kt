package com.dicoding.sortify.data.remote.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(

	@field:SerializedName("message")
	val message: String? = null
) {
	@field:SerializedName("token")
	val token: String? = null
}
