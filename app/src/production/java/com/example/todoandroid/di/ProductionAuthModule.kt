package com.example.todoandroid.di

import com.example.todoandroid.config.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductionAuthModule {

    @Provides
    @Singleton
    fun provideProductionOkHttpClient(
        appConfig: AppConfig
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(appConfig.apiTimeoutSeconds, TimeUnit.SECONDS)

        // No logging interceptor for production
        // No mock interceptor for production

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideProductionRetrofit(
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
