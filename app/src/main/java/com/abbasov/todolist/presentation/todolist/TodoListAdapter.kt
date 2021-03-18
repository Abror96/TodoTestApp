package com.abbasov.todolist.presentation.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abbasov.todolist.databinding.ItemTodoBinding
import com.abbasov.todolist.domain.model.Todo

class TodoListAdapter(
    private var todoList: MutableList<Todo>,
    private val onTodoClickListener: OnTodoClickListener
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(todoList[position]) {
                binding.title.text = title
                binding.description.text = description

                binding.root.setOnClickListener {
                    onTodoClickListener.onClicked(this)
                }
            }
        }
    }

    fun removeAt(index: Int) {
        todoList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int = todoList.size

    fun updateList(todoList: MutableList<Todo>) {
        this.todoList = todoList
        notifyDataSetChanged()
    }


    interface OnTodoClickListener {
        fun onClicked(todo: Todo)
    }

    class ViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)
}