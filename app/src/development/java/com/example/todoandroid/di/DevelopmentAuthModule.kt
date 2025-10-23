package com.example.todoandroid.di

import com.example.todoandroid.config.AppConfig
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
object DevelopmentAuthModule {

    @Provides
    @Singleton
    fun provideDevelopmentOkHttpClient(
        appConfig: AppConfig
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)

        // Add logging interceptor for development
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideDevelopmentRetrofit(
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
