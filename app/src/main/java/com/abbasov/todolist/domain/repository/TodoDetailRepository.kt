package com.abbasov.todolist.domain.repository

import com.abbasov.todolist.domain.model.Todo
import io.reactivex.Single

interface TodoDetailRepository {

    fun addTodo(todo: Todo): Single<Long>

    fun updateTodo(todo: Todo): Single<Int>

}