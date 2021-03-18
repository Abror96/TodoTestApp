package com.abbasov.todolist.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abbasov.todolist.di.util.ViewModelFactory
import com.abbasov.todolist.di.util.ViewModelKey
import com.abbasov.todolist.presentation.tododetail.TodoDetailFragment
import com.abbasov.todolist.presentation.tododetail.TodoDetailViewModel
import com.abbasov.todolist.presentation.todolist.TodoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TodoListViewModel::class)
    internal abstract fun bindsTodoListViewModel(viewModel: TodoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TodoDetailViewModel::class)
    internal abstract fun bindsTodoDetailViewModel(viewModel: TodoDetailViewModel): ViewModel

}