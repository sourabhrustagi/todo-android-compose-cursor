package com.example.todoandroid.di

import com.example.todoandroid.config.AppConfig
import com.example.todoandroid.data.api.interceptor.MockLoginInterceptor
import com.example.todoandroid.data.api.service.AuthApiService
import com.example.todoandroid.data.repository.AuthRepository
import com.example.todoandroid.data.repository.AuthRepositoryImpl
import dagger.Binds
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
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(
            appConfig: AppConfig
        ): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)

            // Add mock interceptor only if configured to use it
            if (appConfig.useMockInterceptor) {
                builder.addInterceptor(MockLoginInterceptor())
            }

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
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            appConfig: AppConfig
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(appConfig.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
            return retrofit.create(AuthApiService::class.java)
        }
    }
}
