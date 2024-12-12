package com.mutdev.soulparkingtest.view

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mutdev.soulparkingtest.R
import com.mutdev.soulparkingtest.data.TodoDatabase
import com.mutdev.soulparkingtest.data.usecase.DeleteLocalTodoUseCase
import com.mutdev.soulparkingtest.data.usecase.InsertLocalTodoUseCase
import com.mutdev.soulparkingtest.data.usecase.LoadLocalTodoListUseCase
import com.mutdev.soulparkingtest.data.usecase.UpdateLocalTodoUseCase
import com.mutdev.soulparkingtest.databinding.ActivityMainBinding
import com.mutdev.soulparkingtest.domain.Todo
import com.mutdev.soulparkingtest.view.adapter.TodoListAdapter
import com.mutdev.soulparkingtest.view.viewmodel.TodoListViewModel
import com.mutdev.soulparkingtest.view.viewmodel.TodoListViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainActivityBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val todoListAdapter by lazy {
        TodoListAdapter {
            lifecycleScope.launch {
                todoListViewModel.updateTodo(it)
            }
        }
    }

    private val todoListViewModel: TodoListViewModel by viewModels {
        val db = TodoDatabase.getInstance(this@MainActivity)
        val dao = db.todoDao()

        val load = LoadLocalTodoListUseCase(dao)
        val insert = InsertLocalTodoUseCase(dao)
        val update = UpdateLocalTodoUseCase(dao)
        val delete = DeleteLocalTodoUseCase(dao)

        TodoListViewModelFactory(load, insert, update, delete)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mainActivityBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(mainActivityBinding) {

            btnAddNew.setOnClickListener {
                startActivity(AddUpdateTodoActivity.moveIntent(this@MainActivity))
            }

            rvTodos.layoutManager = LinearLayoutManager(this@MainActivity)
            rvTodos.adapter = todoListAdapter
            rvTodos.itemAnimator = DefaultItemAnimator()
            rvTodos.setHasFixedSize(false)

            ItemTouchHelper(ItemTouchHelperCallback(this@MainActivity, todoListAdapter) { todo ->
                lifecycleScope.launch {
                    todoListViewModel.deleteTodo(todo)
                }
            }).attachToRecyclerView(rvTodos)
        }

        todoListViewModel.getTodos().observe(this) {
            if(it.isEmpty()) {
                mainActivityBinding.tvEmptyMessage.visibility = RecyclerView.VISIBLE
            } else {
                mainActivityBinding.tvEmptyMessage.visibility = RecyclerView.GONE
            }
            todoListAdapter.submitList(it)
        }
    }
}

class ItemTouchHelperCallback(private val context: Context, private val adapter: TodoListAdapter, private val onDelete: (todo: Todo) -> Unit) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val todo = adapter.currentList[position]

        // Show confirmation dialog
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.delete_todo))
            .setMessage(context.getString(R.string.delete_confirmation_message))
            .setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                onDelete(todo)
            }
            .setNegativeButton(context.getString(R.string.no)) { _, _ ->
                adapter.notifyItemChanged(position)
            }
            .create()
            .show()
    }
}

