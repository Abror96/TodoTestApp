package com.abbasov.todolist

import android.app.Application
import com.abbasov.todolist.di.AppComponent
import com.abbasov.todolist.di.DaggerAppComponent

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppInjector(): AppComponent {
            return appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}