package com.example.todoandroid.data.api.interceptor

import android.util.Log
import com.example.todoandroid.data.api.model.LoginRequest
import com.example.todoandroid.data.api.model.LoginResponse
import com.example.todoandroid.data.api.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class MockLoginInterceptor : Interceptor {
    
    private val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // Only intercept login requests
        if (request.url.encodedPath.contains("/login") && request.method == "POST") {
            return handleLoginRequest(request)
        }
        
        // For other requests, proceed normally
        return chain.proceed(request)
    }
    
    private fun handleLoginRequest(request: Request): Response {
        try {
            // Simulate network delay
            Thread.sleep(1000)
            
            val requestBody = request.body
            val requestBodyString = requestBody?.let { body ->
                val buffer = okio.Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
            } ?: ""
            
            Log.d("MockLoginInterceptor", "Login request body: $requestBodyString")
            
            val loginRequest = try {
                objectMapper.readValue(requestBodyString, LoginRequest::class.java)
            } catch (e: Exception) {
                Log.e("MockLoginInterceptor", "Failed to parse login request", e)
                return createErrorResponse("Invalid request format")
            }
            
            val response = when {
                loginRequest.email == "user@example.com" && loginRequest.password == "password" -> {
                    createSuccessResponse(loginRequest.email)
                }
                loginRequest.email.isBlank() || loginRequest.password.isBlank() -> {
                    createErrorResponse("Email and password are required")
                }
                !isValidEmail(loginRequest.email) -> {
                    createErrorResponse("Please enter a valid email address")
                }
                else -> {
                    createErrorResponse("Invalid email or password")
                }
            }
            
            Log.d("MockLoginInterceptor", "Login response created successfully")
            return response
            
        } catch (e: Exception) {
            Log.e("MockLoginInterceptor", "Error handling login request", e)
            return createErrorResponse("Network error occurred")
        }
    }
    
    private fun createSuccessResponse(email: String): Response {
        val user = User(
            id = "12345",
            email = email,
            name = "Demo User"
        )
        
        val loginResponse = LoginResponse(
            success = true,
            message = "Login successful",
            token = "mock_jwt_token_${System.currentTimeMillis()}",
            user = user
        )
        
        val responseBody = objectMapper.writeValueAsString(loginResponse)
        Log.d("MockLoginInterceptor", "Success response JSON: $responseBody")
        
        return Response.Builder()
            .request(Request.Builder().url("https://api.example.com/login").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(responseBody.toResponseBody("application/json".toMediaType()))
            .addHeader("Content-Type", "application/json")
            .build()
    }
    
    private fun createErrorResponse(message: String): Response {
        val loginResponse = LoginResponse(
            success = false,
            message = message,
            token = null,
            user = null
        )
        
        val responseBody = objectMapper.writeValueAsString(loginResponse)
        Log.d("MockLoginInterceptor", "Error response JSON: $responseBody")
        
        return Response.Builder()
            .request(Request.Builder().url("https://api.example.com/login").build())
            .protocol(Protocol.HTTP_1_1)
            .code(400)
            .message("Bad Request")
            .body(responseBody.toResponseBody("application/json".toMediaType()))
            .addHeader("Content-Type", "application/json")
            .build()
    }
    
    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"))
    }
}
