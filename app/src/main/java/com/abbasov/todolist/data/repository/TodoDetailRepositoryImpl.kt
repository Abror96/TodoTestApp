package com.abbasov.todolist.data.repository

import com.abbasov.todolist.data.source.AppDatabase
import com.abbasov.todolist.domain.model.Todo
import com.abbasov.todolist.domain.repository.TodoDetailRepository
import io.reactivex.Single

class TodoDetailRepositoryImpl(private val appDatabase: AppDatabase) : TodoDetailRepository {
    override fun addTodo(todo: Todo): Single<Long> {
        return appDatabase.todoDao.insert(todo)
    }

    override fun updateTodo(todo: Todo): Single<Int> {
        return appDatabase.todoDao.update(todo)
    }

}