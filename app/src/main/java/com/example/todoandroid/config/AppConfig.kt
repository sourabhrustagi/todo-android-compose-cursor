package com.example.todoandroid.config

interface AppConfig {
    val baseUrl: String
    val useMockInterceptor: Boolean
    val environment: String
    val apiTimeoutSeconds: Long
    val enableLogging: Boolean
}
