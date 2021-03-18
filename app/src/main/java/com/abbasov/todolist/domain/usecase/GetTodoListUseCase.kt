package com.abbasov.todolist.domain.usecase

import com.abbasov.todolist.domain.model.Todo
import com.abbasov.todolist.domain.repository.TodoListRepository
import com.abbasov.todolist.domain.usecase.base.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(private val repository: TodoListRepository) :
    UseCase() {

    fun getList(
        onSuccess: ((t: MutableList<Todo>) -> Unit),
        onError: ((t: Throwable) -> Unit)
    ) {
        disposeLast()
        lastDisposable = repository.getTodoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }

    fun delete(
        todo: Todo,
        onSuccess: (t: Int) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        disposeLast()
        lastDisposable = repository.delete(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }

}