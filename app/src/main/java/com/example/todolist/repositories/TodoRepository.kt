package com.example.todolist.repositories

import com.example.todolist.entities.TodoEntity

interface TodoRepository {
    fun getAll(): List<TodoEntity>

    fun insert(todo: TodoEntity): Boolean

    fun update(todo: TodoEntity): Boolean

    fun delete(todo: TodoEntity): Boolean
}