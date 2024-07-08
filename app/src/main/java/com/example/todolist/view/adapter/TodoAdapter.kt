package com.example.todolist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.TodoItemBinding
import com.example.todolist.entities.TodoEntity
import com.example.todolist.view.listener.TodoListener

class TodoAdapter(private val listener: TodoListener) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var todoList: List<TodoEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val item = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(item)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    inner class TodoViewHolder(private val itemBinding: TodoItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(todo: TodoEntity) {
            itemBinding.title.text = todo.title
            itemBinding.done.isChecked = todo.done
            itemBinding.done.setOnClickListener {
                val newTodo = todo.copy(done = !todo.done)
                listener.onUpdate(newTodo)
            }
            itemBinding.row.setOnLongClickListener {
                AlertDialog.Builder(itemView.context).setTitle(R.string.delete)
                    .setMessage(R.string.are_you_sure_you_want_to_remove_this_item)
                    .setNegativeButton(R.string.no, null)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        listener.onDelete(todo)
                    }.show()
                true
            }
        }
    }

    fun updateList(newList: List<TodoEntity>) {
        todoList = newList
        notifyDataSetChanged()
    }
}