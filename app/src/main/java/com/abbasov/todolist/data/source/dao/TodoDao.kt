package com.abbasov.todolist.data.source.dao

import androidx.room.*
import com.abbasov.todolist.domain.model.Todo
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo): Single<Long>

    @Query("select * from todo")
    fun getAll(): Single<MutableList<Todo>>

    @Update
    fun update(todo: Todo): Single<Int>

    @Delete
    fun delete(todo: Todo): Single<Int>

}