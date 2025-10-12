package com.example.todoandroidcursor.data.repository

import com.example.todoandroidcursor.data.api.model.LoginRequest
import com.example.todoandroidcursor.data.api.model.LoginResponse
import com.example.todoandroidcursor.data.api.service.AuthApiService
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
    private val authApiService: AuthApiService
) : AuthRepository {
    
    private val _isLoggedIn = MutableStateFlow(false)
    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    private val _currentUser = MutableStateFlow<UserInfo?>(null)
    override val currentUser: StateFlow<UserInfo?> = _currentUser.asStateFlow()
    
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
        _isLoggedIn.value = false
        _currentUser.value = null
    }
}
