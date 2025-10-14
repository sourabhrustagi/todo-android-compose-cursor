package com.example.todoandroid.config

import com.example.todoandroid.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductionAppConfig @Inject constructor() : AppConfig {
    override val baseUrl: String = BuildConfig.BASE_URL
    override val useMockInterceptor: Boolean = BuildConfig.USE_MOCK_INTERCEPTOR
    override val environment: String = BuildConfig.ENVIRONMENT
    override val apiTimeoutSeconds: Long = 15L
    override val enableLogging: Boolean = false
}
