package com.abbasov.todolist.presentation.tododetail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.abbasov.todolist.App
import com.abbasov.todolist.R
import com.abbasov.todolist.databinding.FragmentTodoDetailBinding
import com.abbasov.todolist.domain.model.Todo
import com.abbasov.todolist.utils.Constants.TODO_TYPE
import com.abbasov.todolist.utils.Type
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class TodoDetailFragment : Fragment() {

    private lateinit var binding: FragmentTodoDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TodoDetailViewModel by viewModels() { viewModelFactory }
    private var id: Long? = null

    companion object {
        fun newInstance(todo: Todo?): TodoDetailFragment {
            val fragment = TodoDetailFragment()
            fragment.arguments = bundleOf(
                "title" to todo?.title,
                "descr" to todo?.description,
                "id" to todo?.id
            )
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        App.getAppInjector().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = arguments?.getString("title") ?: getString(R.string.adding_todo)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        id = arguments?.getLong("id")
        binding.etTitle.setText(arguments?.getString("title"))
        binding.etDescription.setText(arguments?.getString("descr"))
        binding.save.text =
            if (TODO_TYPE == Type.NEW) getString(R.string.add) else getString(R.string.save)

        viewModel.vmState.observe(viewLifecycleOwner, {
            when (it) {
                is TodoDetailViewModel.VMState.SuccessState -> {
                    Snackbar.make(binding.root, it.result, Snackbar.LENGTH_LONG).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        setResult()
                        parentFragmentManager.popBackStack()
                    }, 1000)
                }
                is TodoDetailViewModel.VMState.ErrorState -> {
                    Snackbar.make(
                        binding.root,
                        it.message ?: getString(R.string.error_has_occured),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        binding.save.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            if (TODO_TYPE == Type.NEW) {
                viewModel.insert(title, description)
            } else {
                viewModel.update(id ?: 0, title, description)
            }
        }
    }

    private fun setResult() {
        setFragmentResult(
            "todo_detail", bundleOf(
                "status_code" to 1000
            )
        )
    }

}