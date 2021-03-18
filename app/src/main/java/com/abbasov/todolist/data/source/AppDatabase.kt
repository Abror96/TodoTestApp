package com.abbasov.todolist.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abbasov.todolist.data.source.dao.TodoDao
import com.abbasov.todolist.domain.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {
        const val DB_NAME = "TodoListDatabase.db"
    }
}