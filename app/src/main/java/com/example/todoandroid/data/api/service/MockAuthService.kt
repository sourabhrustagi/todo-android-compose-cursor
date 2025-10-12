package com.example.todoandroid.data.api.service

import com.example.todoandroid.data.api.model.LoginRequest
import com.example.todoandroid.data.api.model.LoginResponse
import com.example.todoandroid.data.api.model.User
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAuthService @Inject constructor() : AuthApiService {
    override suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        // Simulate network delay
        kotlinx.coroutines.delay(1000)

        val response = when {
            loginRequest.email == "user@example.com" && loginRequest.password == "password" -> {
                LoginResponse(
                    success = true,
                    message = "Login successful",
                    token = "mock_jwt_token_${System.currentTimeMillis()}",
                    user = User(
                        id = "123456",
                        email = loginRequest.email,
                        name = "Demo User"
                    )
                )
            }

            loginRequest.email.isBlank() || loginRequest.password.isBlank() -> {
                LoginResponse(
                    success = false,
                    message = "Email and password are required",
                    token = null,
                    user = null
                )
            }

            !isValidEmail(loginRequest.email) -> {
                LoginResponse(
                    success = false,
                    message = "Please enter a valid email address",
                    token = null,
                    user = null
                )
            }

            else -> {
                LoginResponse(
                    success = false,
                    message = "Invalid email or password",
                    token = null,
                    user = null
                )
            }
        }

        return Response.success(response)
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"))
    }
}