package com.example.todolist.view.listener

import com.example.todolist.entities.TodoEntity

interface TodoListener {
    fun onUpdate(todo: TodoEntity)
    fun onDelete(todo: TodoEntity)
}