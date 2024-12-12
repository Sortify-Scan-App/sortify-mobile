package com.dicoding.sortify.data.remote.retrofit

import com.dicoding.sortify.data.pref.ResetPasswordRequest
import com.dicoding.sortify.data.pref.UpdateEmailRequest
import com.dicoding.sortify.data.pref.UpdateFullNameRequest
import com.dicoding.sortify.data.remote.response.ArticleResponse
import com.dicoding.sortify.data.remote.response.LoginResponse
import com.dicoding.sortify.data.remote.response.ModelResponse
import com.dicoding.sortify.data.remote.response.OtpResponse
import com.dicoding.sortify.data.remote.response.RegisterResponse
import com.dicoding.sortify.data.remote.response.RequestOtpResponse
import com.dicoding.sortify.data.remote.response.ResetPasswordResponse
import com.dicoding.sortify.data.remote.response.UpdateProfileResponse
import com.dicoding.sortify.data.remote.response.VerifyOtpResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("articles")
    suspend fun getArticles(): ArticleResponse

    @Multipart
    @POST("predict")
    suspend fun uploadWaste(
        @Part file: MultipartBody.Part,
    ): ModelResponse

    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun register(
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/verify-otp")
    suspend fun verifyOtp(
        @Field("email") email: String,
        @Field("otp") password: String
    ): OtpResponse

    @FormUrlEncoded
    @POST("auth/forgot-password/verify-otp")
    suspend fun verifyOtpPassword(
        @Field("email") email: String,
        @Field("otp") password: String
    ): VerifyOtpResponse

    @FormUrlEncoded
    @POST("auth/forgot-password/request-otp")
    suspend fun requestOtp(
        @Field("email") email: String,
    ): RequestOtpResponse

    @POST("auth/forgot-password/reset-password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): ResetPasswordResponse

    @POST("auth/profile/update-fullname")
    suspend fun updateFullName(
        @Body request: UpdateFullNameRequest
    ): UpdateProfileResponse

    @POST("auth/profile/update-email")
    suspend fun updateEmail(
        @Body request: UpdateEmailRequest
    ): UpdateProfileResponse

}
