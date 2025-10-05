package com.example.todoandroidcursor.di

import com.example.todoandroidcursor.data.InMemoryTodoRepository
import com.example.todoandroidcursor.data.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoModule {

    @Binds
    @Singleton
    abstract fun bindTodoRepository(
        inMemoryTodoRepository: InMemoryTodoRepository
    ): TodoRepository
}



