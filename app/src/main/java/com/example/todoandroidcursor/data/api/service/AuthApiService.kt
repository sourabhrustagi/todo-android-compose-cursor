package com.example.todoandroidcursor.data.api.service

import com.example.todoandroidcursor.data.api.model.LoginRequest
import com.example.todoandroidcursor.data.api.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}

