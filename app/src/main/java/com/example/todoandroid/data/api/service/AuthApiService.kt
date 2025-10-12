package com.example.todoandroid.data.api.service

import com.example.todoandroid.data.api.model.LoginRequest
import com.example.todoandroid.data.api.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}

