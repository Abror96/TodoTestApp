package com.abbasov.todolist.domain.repository

import com.abbasov.todolist.domain.model.Todo
import io.reactivex.Single

interface TodoListRepository {

    fun getTodoList(): Single<MutableList<Todo>>

    fun delete(todo: Todo): Single<Int>

}