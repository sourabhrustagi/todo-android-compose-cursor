package com.example.todoandroidcursor.di

import com.example.todoandroidcursor.data.api.service.AuthApiService
import com.example.todoandroidcursor.data.api.service.MockAuthService
import com.example.todoandroidcursor.data.repository.AuthRepository
import com.example.todoandroidcursor.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthApiService(
        mockAuthService: MockAuthService
    ): AuthApiService

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}

