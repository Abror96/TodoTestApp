package com.abbasov.todolist.presentation.todolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.abbasov.todolist.R
import com.abbasov.todolist.domain.model.Todo
import com.abbasov.todolist.domain.usecase.GetTodoListUseCase
import com.abbasov.todolist.presentation.tododetail.TodoDetailViewModel
import com.abbasov.todolist.utils.MultipleLiveEvent
import javax.inject.Inject

class TodoListViewModel @Inject constructor(
    private val application: Application,
    private val getTodoListUseCase: GetTodoListUseCase
) : ViewModel() {

    var vmState = MultipleLiveEvent<VMState>()

    fun getTodoList() {
        getTodoListUseCase.getList(
            onSuccess = {
                vmState.postValue(VMState.SuccessGetTodoListState(it))
            },
            onError = {
                vmState.postValue(VMState.ErrorState(it.localizedMessage))
            }
        )
    }

    fun delete(todo: Todo) {
        getTodoListUseCase.delete(
            todo,
            onSuccess = {
                vmState.postValue(VMState.SuccessDeleteState(application.getString(R.string.successfully_deleted)))
            },
            onError = {
                vmState.postValue(VMState.ErrorState(it.localizedMessage))
            }
        )
    }

    sealed class VMState {
        class ErrorState(val message: String?) : VMState()
        class SuccessGetTodoListState(val result: MutableList<Todo>) : VMState()
        class SuccessDeleteState(val result: String) : VMState()
    }

}