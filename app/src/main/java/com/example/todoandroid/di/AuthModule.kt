package com.example.todoandroid.di

import com.example.todoandroid.data.api.service.AuthApiService
import com.example.todoandroid.data.api.service.MockAuthService
import com.example.todoandroid.data.repository.AuthRepository
import com.example.todoandroid.data.repository.AuthRepositoryImpl
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

