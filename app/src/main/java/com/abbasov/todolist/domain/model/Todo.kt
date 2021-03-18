package com.abbasov.todolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val title: String?,
    val description: String?
)
