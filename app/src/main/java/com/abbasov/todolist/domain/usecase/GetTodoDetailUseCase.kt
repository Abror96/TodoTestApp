package com.abbasov.todolist.domain.usecase

import com.abbasov.todolist.domain.model.Todo
import com.abbasov.todolist.domain.repository.TodoDetailRepository
import com.abbasov.todolist.domain.usecase.base.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.single.SingleDoOnSuccess
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetTodoDetailUseCase @Inject constructor(private val repository: TodoDetailRepository) :
    UseCase() {

    fun insert(
        todo: Todo,
        onSuccess: ((t: Long) -> Unit),
        onError: ((t: Throwable) -> Unit)
    ) {
        disposeLast()
        lastDisposable = repository.addTodo(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }

    fun update(
        todo: Todo,
        onSuccess: (t: Int) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        disposeLast()
        lastDisposable = repository.updateTodo(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }
}