package com.example.todoandroid.di

import com.example.todoandroid.data.InMemoryTodoRepository
import com.example.todoandroid.data.TodoRepository
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



