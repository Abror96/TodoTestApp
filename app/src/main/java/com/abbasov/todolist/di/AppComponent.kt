package com.abbasov.todolist.di

import android.app.Application
import com.abbasov.todolist.presentation.MainActivity
import com.abbasov.todolist.di.module.AppModule
import com.abbasov.todolist.di.module.DatabaseModule
import com.abbasov.todolist.di.module.ViewModelModule
import com.abbasov.todolist.presentation.tododetail.TodoDetailFragment
import com.abbasov.todolist.presentation.todolist.TodoListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: TodoListFragment)
    fun inject(fragment: TodoDetailFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}