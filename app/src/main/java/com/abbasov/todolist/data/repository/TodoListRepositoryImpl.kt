package com.abbasov.todolist.data.repository

import com.abbasov.todolist.data.source.AppDatabase
import com.abbasov.todolist.domain.model.Todo
import com.abbasov.todolist.domain.repository.TodoListRepository
import io.reactivex.Single

class TodoListRepositoryImpl(private val appDatabase: AppDatabase) : TodoListRepository {
    override fun getTodoList(): Single<MutableList<Todo>> {
        return appDatabase.todoDao.getAll()
    }

    override fun delete(todo: Todo): Single<Int> {
        return appDatabase.todoDao.delete(todo)
    }
}