package com.abbasov.todolist.presentation.tododetail

import android.app.Application
import android.provider.Settings.Global.getString
import androidx.lifecycle.ViewModel
import com.abbasov.todolist.R
import com.abbasov.todolist.domain.model.Todo
import com.abbasov.todolist.domain.usecase.GetTodoDetailUseCase
import com.abbasov.todolist.utils.MultipleLiveEvent
import javax.inject.Inject

class TodoDetailViewModel @Inject constructor(
    private val application: Application,
    private val getTodoDetailUseCase: GetTodoDetailUseCase
) : ViewModel() {

    var vmState = MultipleLiveEvent<VMState>()

    fun insert(title: String, descr: String) {
        if (title.isNotEmpty() && descr.isNotEmpty()) {
            getTodoDetailUseCase.insert(
                Todo(title = title, description = descr),
                onSuccess = {
                    vmState.postValue(VMState.SuccessState(application.getString(R.string.successfully_added)))
                },
                onError = {
                    vmState.postValue(VMState.ErrorState(it.localizedMessage))
                })
        } else {
            vmState.postValue(VMState.ErrorState(application.getString(R.string.all_fields_are_required_to_fill)))
        }
    }

    fun update(id: Long, title: String, descr: String) {
        if (title.isNotEmpty() && descr.isNotEmpty()) {
            getTodoDetailUseCase.update(
                Todo(id = id, title = title, description = descr),
                onSuccess = {
                    vmState.postValue(VMState.SuccessState(application.getString(R.string.successfully_edited)))
                },
                onError = {
                    vmState.postValue(VMState.ErrorState(it.localizedMessage))
                })
        } else {
            vmState.postValue(VMState.ErrorState(application.getString(R.string.all_fields_are_required_to_fill)))
        }
    }

    sealed class VMState {
        class ErrorState(val message: String?) : VMState()
        class SuccessState(val result: String) : VMState()
    }
}