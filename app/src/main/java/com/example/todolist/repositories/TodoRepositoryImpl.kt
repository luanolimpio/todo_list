package com.example.todolist.repositories

import com.example.todolist.database.TodoDao
import com.example.todolist.entities.TodoEntity

class TodoRepositoryImpl(private val todoDao: TodoDao) : TodoRepository {
    override fun getAll(): List<TodoEntity> {
        return todoDao.getAll()
    }

    override fun insert(todo: TodoEntity): Boolean {
        return todoDao.insert(todo) > 0
    }

    override fun update(todo: TodoEntity): Boolean {
        return todoDao.update(todo) > 0
    }

    override fun delete(todo: TodoEntity): Boolean {
        return todoDao.delete(todo) > 0
    }
}