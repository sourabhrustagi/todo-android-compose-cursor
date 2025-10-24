package com.example.todoandroid.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.todoandroid.data.api.model.LoginRequest
import com.example.todoandroid.data.api.model.LoginResponse
import com.example.todoandroid.data.api.service.AuthApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepository {
    val isLoggedIn: StateFlow<Boolean>
    val currentUser: StateFlow<UserInfo?>
    suspend fun login(email: String, password: String): Result<LoginResponse>
    fun logout()
}

data class UserInfo(
    val id: String,
    val email: String,
    val name: String,
    val token: String
)

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val context: Context
) : AuthRepository {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    
    private val _isLoggedIn = MutableStateFlow(sharedPreferences.getBoolean("is_logged_in", false))
    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    private val _currentUser = MutableStateFlow<UserInfo?>(getStoredUser())
    override val currentUser: StateFlow<UserInfo?> = _currentUser.asStateFlow()
    
    init {
        // Debug logging
        android.util.Log.d("AuthRepository", "Initial login state: ${_isLoggedIn.value}")
        android.util.Log.d("AuthRepository", "Initial user: ${_currentUser.value}")
    }
    
    private fun getStoredUser(): UserInfo? {
        val userId = sharedPreferences.getString("user_id", null)
        val userEmail = sharedPreferences.getString("user_email", null)
        val userName = sharedPreferences.getString("user_name", null)
        val userToken = sharedPreferences.getString("user_token", null)
        
        return if (userId != null && userEmail != null && userName != null && userToken != null) {
            UserInfo(id = userId, email = userEmail, name = userName, token = userToken)
        } else {
            null
        }
    }
    
    private fun saveUser(userInfo: UserInfo) {
        sharedPreferences.edit().apply {
            putBoolean("is_logged_in", true)
            putString("user_id", userInfo.id)
            putString("user_email", userInfo.email)
            putString("user_name", userInfo.name)
            putString("user_token", userInfo.token)
            apply()
        }
    }
    
    private fun clearUser() {
        sharedPreferences.edit().apply {
            putBoolean("is_logged_in", false)
            remove("user_id")
            remove("user_email")
            remove("user_name")
            remove("user_token")
            apply()
        }
    }
    
    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val loginRequest = LoginRequest(email = email, password = password)
            val response = authApiService.login(loginRequest)
            
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                if (loginResponse.success) {
                    val userInfo = UserInfo(
                        id = loginResponse.user?.id ?: "",
                        email = loginResponse.user?.email ?: email,
                        name = loginResponse.user?.name ?: "User",
                        token = loginResponse.token ?: ""
                    )
                    _currentUser.value = userInfo
                    _isLoggedIn.value = true
                    saveUser(userInfo)
                    android.util.Log.d("AuthRepository", "User logged in successfully: ${userInfo.email}")
                }
                Result.success(loginResponse)
            } else {
                val errorResponse = LoginResponse(
                    success = false,
                    message = "Network error occurred",
                    token = null,
                    user = null
                )
                Result.success(errorResponse)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun logout() {
        android.util.Log.d("AuthRepository", "User logging out")
        _isLoggedIn.value = false
        _currentUser.value = null
        clearUser()
        android.util.Log.d("AuthRepository", "User logged out successfully")
    }
}
