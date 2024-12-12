package com.dicoding.sortify.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ModelResponse(

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("class")
	val wasteClass: String? = null,

	@field:SerializedName("confidence")
	val confidence: Float? = null,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem?>? = null
)

@Parcelize
data class RecommendationsItem(
	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable
