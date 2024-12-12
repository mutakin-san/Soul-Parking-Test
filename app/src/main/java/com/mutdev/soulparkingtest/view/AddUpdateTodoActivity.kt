package com.mutdev.soulparkingtest.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.mutdev.soulparkingtest.R
import com.mutdev.soulparkingtest.data.TodoDatabase
import com.mutdev.soulparkingtest.data.usecase.DeleteLocalTodoUseCase
import com.mutdev.soulparkingtest.data.usecase.InsertLocalTodoUseCase
import com.mutdev.soulparkingtest.data.usecase.LoadLocalTodoListUseCase
import com.mutdev.soulparkingtest.data.usecase.UpdateLocalTodoUseCase
import com.mutdev.soulparkingtest.databinding.ActivityAddTodoBinding
import com.mutdev.soulparkingtest.domain.Todo
import com.mutdev.soulparkingtest.view.viewmodel.TodoListViewModel
import com.mutdev.soulparkingtest.view.viewmodel.TodoListViewModelFactory
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddUpdateTodoActivity : AppCompatActivity() {
    private val addTodoActivityBinding by lazy {
        ActivityAddTodoBinding.inflate(layoutInflater)
    }

    private val todoListViewModel: TodoListViewModel by viewModels {
        val db = TodoDatabase.getInstance(this@AddUpdateTodoActivity)
        val dao = db.todoDao()

        val load = LoadLocalTodoListUseCase(dao)
        val insert = InsertLocalTodoUseCase(dao)
        val update = UpdateLocalTodoUseCase(dao)
        val delete = DeleteLocalTodoUseCase(dao)

        TodoListViewModelFactory(load, insert, update, delete)
    }

    private lateinit var todo: Todo
    private var chosenDate: Date? = null
    private val dateFormatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, Locale.getDefault())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addTodoActivityBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(addTodoActivityBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra(EXTRA_ID, -1)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val date = intent.getLongExtra(EXTRA_DATE_IN_MILLIS, 0L)

        todo = Todo(
            id = if (id == -1) null else id,
            title = title ?: "",
            description = description ?: "",
            date = Date(date)
        )

        addTodoActivityBinding.etTitle.setText(todo.title)
        addTodoActivityBinding.etDescription.setText(todo.description)
        if(date != 0L) {
            chosenDate = todo.date
            addTodoActivityBinding.etDate.setText(dateFormatter.format(todo.date))
        }
        if(id == -1) {
            addTodoActivityBinding.btnSave.text = getString(R.string.save_todo)
        } else {
            addTodoActivityBinding.btnSave.text = getString(R.string.update_todo)
        }

        addTodoActivityBinding.etDate.setOnClickListener {
            showDateTimePicker()
        }


        addTodoActivityBinding.btnSave.setOnClickListener {
            saveTodo()
        }


        todoListViewModel.validationErrors.observe(this) { errors ->

            with(addTodoActivityBinding) {
                etTitle.error = null
                etDate.error = null

                errors["title"]?.let {
                    etTitle.error = it
                }
                errors["date"]?.let {
                    etDate.error = it
                }

                Toast.makeText(this@AddUpdateTodoActivity,
                    getString(R.string.invalid_input_message), Toast.LENGTH_SHORT).show()
            }
        }

    }



    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

        // DatePickerDialog
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)

            // TimePickerDialog
            TimePickerDialog(this, { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                if (calendar.timeInMillis < System.currentTimeMillis()) {
                    Toast.makeText(this, resources.getString(R.string.invalid_date_message), Toast.LENGTH_SHORT).show()
                } else {
                    addTodoActivityBinding.etDate.setText(dateFormatter.format(calendar.time))
                    chosenDate = calendar.time
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun saveTodo() {
        val title = addTodoActivityBinding.etTitle.text.toString().trim()
        val description = addTodoActivityBinding.etDescription.text.toString().trim()

        if (!todoListViewModel.validateTodo(title, chosenDate)) {
            return
        }

        lifecycleScope.launch {
            try {
                val newTodo = todo.copy(
                    title = title,
                    description = description,
                    date = chosenDate!!
                )

                if (newTodo.id == null) {
                    todoListViewModel.insertTodo(newTodo)
                } else {
                    todoListViewModel.updateTodo(newTodo)
                }
                Toast.makeText(this@AddUpdateTodoActivity,
                    getString(R.string.todo_saved_successfully), Toast.LENGTH_SHORT).show()

                finish()
            } catch (e: Exception) {
                e.printStackTrace()

                Toast.makeText(this@AddUpdateTodoActivity,
                    getString(R.string.failed_save_todo_message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val EXTRA_ID: String = "extra_id"
        const val EXTRA_TITLE: String = "extra_title"
        const val EXTRA_DESCRIPTION: String = "extra_description"
        const val EXTRA_DATE_IN_MILLIS: String = "extra_date"

        fun moveIntent(context: Context): Intent {
            return Intent(context, AddUpdateTodoActivity::class.java)
        }
    }
}