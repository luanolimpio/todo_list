package com.example.todolist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.entities.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getAll(): List<TodoEntity>

    @Insert
    fun insert(todo: TodoEntity): Long

    @Update()
    fun update(todo: TodoEntity): Int

    @Delete
    fun delete(todo: TodoEntity): Int
}