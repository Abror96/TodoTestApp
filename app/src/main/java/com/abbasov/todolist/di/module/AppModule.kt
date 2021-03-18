package com.abbasov.todolist.di.module

import com.abbasov.todolist.data.repository.TodoDetailRepositoryImpl
import com.abbasov.todolist.data.repository.TodoListRepositoryImpl
import com.abbasov.todolist.data.source.AppDatabase
import com.abbasov.todolist.domain.repository.TodoDetailRepository
import com.abbasov.todolist.domain.repository.TodoListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideTodoRepository(
        appDatabase: AppDatabase
    ): TodoListRepository {
        return TodoListRepositoryImpl(appDatabase)
    }

    @Singleton
    @Provides
    fun provideTodoDetailRepository(
        appDatabase: AppDatabase
    ): TodoDetailRepository {
        return TodoDetailRepositoryImpl(appDatabase)
    }

}