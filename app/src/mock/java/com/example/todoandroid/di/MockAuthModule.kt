package com.example.todoandroid.di

import com.example.todoandroid.config.AppConfig
import com.example.todoandroid.data.api.interceptor.MockLoginInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockAuthModule {

    @Provides
    @Singleton
    fun provideMockOkHttpClient(
        appConfig: AppConfig
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)

        // Add mock interceptor for mock flavor
        builder.addInterceptor(MockLoginInterceptor())

        // Add logging interceptor only if logging is enabled
        if (appConfig.enableLogging) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMockRetrofit(
        okHttpClient: OkHttpClient,
        appConfig: AppConfig
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(appConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}
