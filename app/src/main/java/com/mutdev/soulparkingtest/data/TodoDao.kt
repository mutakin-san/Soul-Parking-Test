package com.mutdev.soulparkingtest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getTodos(): LiveData<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: TodoEntity): Long

    @Delete
    suspend fun deleteTodo(todo: TodoEntity): Int

    @Update
    suspend fun updateTodo(todo: TodoEntity): Int
}