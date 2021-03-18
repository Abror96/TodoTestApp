package com.abbasov.todolist.presentation.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abbasov.todolist.App
import com.abbasov.todolist.R
import com.abbasov.todolist.databinding.FragmentTodoListBinding
import com.abbasov.todolist.domain.model.Todo
import com.abbasov.todolist.presentation.tododetail.TodoDetailFragment
import com.abbasov.todolist.presentation.todolist.TodoListViewModel.VMState.*
import com.abbasov.todolist.utils.Constants.TODO_TYPE
import com.abbasov.todolist.utils.Type
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class TodoListFragment : Fragment(), TodoListAdapter.OnTodoClickListener {

    private val TAG = "LOGGERR"
    private lateinit var binding: FragmentTodoListBinding
    private var todoList: MutableList<Todo> = ArrayList()
    private lateinit var adapter: TodoListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TodoListViewModel by viewModels() { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        App.getAppInjector().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        viewModel.vmState.observe(viewLifecycleOwner, {
            when (it) {
                is SuccessGetTodoListState -> {
                    todoList = it.result
                    Log.d(TAG, "SuccessGetTodoListState: $todoList")
                    adapter.updateList(todoList)
                }
                is SuccessDeleteState -> {
                    Snackbar.make(binding.root, it.result, Snackbar.LENGTH_LONG).show()
                }
                is ErrorState -> {
                    Log.d(TAG, "get todo list: ${it.message}")
                }
            }
        })
        viewModel.getTodoList()

        binding.addTodo.setOnClickListener {
            TODO_TYPE = Type.NEW
            parentFragmentManager.beginTransaction()
                .add(R.id.main_container, TodoDetailFragment.newInstance(null), "todo_detail")
                .addToBackStack("todo_detail").commit()
        }

        setFragmentResultListener("todo_detail") { _, bundle ->
            val statusCode = bundle.getInt("status_code", 0)
            if (statusCode == 1000) {
                viewModel.getTodoList()
            }
        }
    }

    private fun initRecycler() {
        adapter = TodoListAdapter(todoList, this)

        binding.todoRecycler.layoutManager = LinearLayoutManager(context)
        binding.todoRecycler.adapter = adapter

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                val position = h.adapterPosition
                viewModel.delete(todoList[position])
                adapter.removeAt(position)
            }
        }).attachToRecyclerView(binding.todoRecycler)
    }

    override fun onClicked(todo: Todo) {
        TODO_TYPE = Type.EDIT
        parentFragmentManager.beginTransaction()
            .add(R.id.main_container, TodoDetailFragment.newInstance(todo), "todo_detail")
            .addToBackStack("todo_detail").commit()
    }

}