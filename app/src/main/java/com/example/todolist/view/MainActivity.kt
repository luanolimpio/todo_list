package com.example.todolist.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.entities.TodoEntity
import com.example.todolist.view.adapter.TodoAdapter
import com.example.todolist.view.listener.TodoListener
import com.example.todolist.viewmodels.TodoViewModel
import com.example.todolist.viewmodels.TodoViewModelFactory
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainActivity : AppCompatActivity(), TodoListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this, TodoViewModelFactory(this))[TodoViewModel::class.java]

        adapter = TodoAdapter(this)
        binding.contentMain.todoRecyclerView.adapter = adapter
        binding.contentMain.todoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.contentMain.todoRecyclerView.addItemDecoration(
            MaterialDividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            ).apply {
                isLastItemDecorated = false
            }
        )

        viewModel.getAll()
        observer()

        binding.contentMain.addButton.setOnClickListener {
            addTodo()
        }

        binding.contentMain.mainEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addTodo()
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun observer() {
        viewModel.todoList.observe(this) {
            adapter.updateList(it)
            checkListVisibility(it)
        }
    }

    private fun addTodo() {
        val text = binding.contentMain.mainEditText.text.toString().trim()
        if (text.isNotEmpty()) {
            viewModel.insert(text)
            binding.contentMain.mainEditText.text.clear()
        }
    }

    private fun checkListVisibility(list: List<TodoEntity>) {
        if (list.isNotEmpty()) {
            binding.contentMain.emptyState.visibility = View.GONE
            binding.contentMain.todoRecyclerView.visibility = View.VISIBLE
        } else {
            binding.contentMain.emptyState.visibility = View.VISIBLE
            binding.contentMain.todoRecyclerView.visibility = View.GONE
        }
    }

    override fun onUpdate(todo: TodoEntity) {
        viewModel.update(todo)
    }

    override fun onDelete(todo: TodoEntity) {
        viewModel.delete(todo)
    }
}