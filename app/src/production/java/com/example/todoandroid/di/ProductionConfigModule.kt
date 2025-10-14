package com.example.todoandroid.di

import com.example.todoandroid.config.AppConfig
import com.example.todoandroid.config.ProductionAppConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductionConfigModule {

    @Binds
    @Singleton
    abstract fun bindAppConfig(productionAppConfig: ProductionAppConfig): AppConfig
}
