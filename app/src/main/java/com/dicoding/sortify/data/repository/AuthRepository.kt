package com.dicoding.sortify.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.sortify.data.pref.UpdateEmailRequest
import com.dicoding.sortify.data.pref.UpdateFullNameRequest
import com.dicoding.sortify.data.pref.UserModel
import com.dicoding.sortify.data.pref.UserPreference
import com.dicoding.sortify.data.remote.response.LoginResponse
import com.dicoding.sortify.data.remote.response.OtpResponse
import com.dicoding.sortify.data.remote.response.RegisterResponse
import com.dicoding.sortify.data.remote.response.UpdateProfileResponse
import com.dicoding.sortify.data.remote.response.VerifyOtpResponse
import com.dicoding.sortify.data.remote.retrofit.ApiConfig
import com.dicoding.sortify.data.remote.retrofit.ApiService
import com.dicoding.sortify.helper.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class AuthRepository private constructor(
    private var apiService: ApiService,
    private val userPreference: UserPreference,
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> = userPreference.getSession()

    fun register(
        username: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.register(username, email, password)
            emit(Result.Success(result))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "Registration error"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred: ${e.message}"))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.login(email, password)
            emit(Result.Success(result))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "Login error"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred: ${e.message}"))
        }
    }


    suspend fun updateFullName(token: String, newFullName: String): UpdateProfileResponse {
        return apiService.updateFullName(UpdateFullNameRequest(token, newFullName))
    }

    suspend fun updateEmail(token: String, newEmail: String): UpdateProfileResponse {
        return apiService.updateEmail(UpdateEmailRequest(token, newEmail))
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun verifyOtp(
        email: String,
        otp: String
    ): LiveData<Result<OtpResponse>> = liveData {
        emit(Result.Loading)
        try {
            Log.d("OtpRepository", "Attempting OTP verification for email: $email")

            val result = apiService.verifyOtp(email, otp)

            Log.d("OtpRepository", "OTP Response: ${result.message}, Token: ${result.token}")

            emit(Result.Success(result))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("OtpRepository", "HTTP Error: ${e.code()}, Body: $errorBody")

            val errorMessage = when (e.code()) {
                400 -> "Invalid OTP or request"
                401 -> "Authentication failed"
                404 -> "User not found"
                500 -> "Server error"
                else -> "Error ${e.code()}: ${errorBody ?: e.message()}"
            }
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.e("OtpRepository", "Unexpected error", e)
            emit(Result.Error("Unexpected error: ${e.message ?: "Unknown error"}"))
        }
    }

    fun verifyOtpPassword(
        email: String,
        otp: String
    ): LiveData<Result<VerifyOtpResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.verifyOtpPassword(email, otp)
            emit(Result.Success(result))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = when (e.code()) {
                400 -> "Invalid OTP or request"
                401 -> "Authentication failed"
                404 -> "User not found"
                500 -> "Server error"
                else -> "Error ${e.code()}: ${errorBody ?: e.message()}"
            }
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.e("OtpRepository", "Unexpected error", e)
            emit(Result.Error("Unexpected error: ${e.message ?: "Unknown error"}"))
        }
    }

    fun requestOtp(email: String): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.requestOtp(email)
            val otpMessage = result.message ?: "OTP request failed, no message provided"
            emit(Result.Success(otpMessage))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "OTP request error"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred: ${e.message}"))
        }
    }


    companion object {
        fun getInstance(
            userPref: UserPreference
        ): AuthRepository {
            return AuthRepository(ApiConfig.getAuthService(), userPref)
        }
    }
}
