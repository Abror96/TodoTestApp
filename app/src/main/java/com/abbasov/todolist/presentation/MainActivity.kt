package com.abbasov.todolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abbasov.todolist.App.Companion.getAppInjector
import com.abbasov.todolist.R
import com.abbasov.todolist.databinding.ActivityMainBinding
import com.abbasov.todolist.presentation.todolist.TodoListFragment
import com.abbasov.todolist.presentation.todolist.TodoListFragment_MembersInjector

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fm.beginTransaction()
            .add(R.id.main_container, TodoListFragment(), "todo_list")
            .commit()
    }
}