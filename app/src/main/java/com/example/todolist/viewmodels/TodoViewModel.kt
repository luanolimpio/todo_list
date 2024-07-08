package com.example.todolist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.entities.TodoEntity
import com.example.todolist.repositories.TodoRepository

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _todoList = MutableLiveData<List<TodoEntity>>(emptyList())
    val todoList: LiveData<List<TodoEntity>> = _todoList

    fun getAll() {
        _todoList.value = repository.getAll()
    }

    fun insert(title: String) {
        val todo = TodoEntity(null, title, false)
        val inserted = repository.insert(todo)
        if (inserted) getAll()
    }

    fun update(todo: TodoEntity) {
        repository.update(todo)
    }

    fun delete(todo: TodoEntity) {
        val deleted = repository.delete(todo)
        if (deleted) getAll()
    }
}