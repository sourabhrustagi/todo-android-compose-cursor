package com.example.todoandroid.data.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse(
    @JsonProperty("success")
    val success: Boolean,
    @JsonProperty("message")
    val message: String,
    @JsonProperty("token")
    val token: String? = null,
    @JsonProperty("user")
    val user: User? = null
)

data class User(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("email")
    val email: String,
    @JsonProperty("name")
    val name: String
)
