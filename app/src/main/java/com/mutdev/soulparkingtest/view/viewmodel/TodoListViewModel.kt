package com.mutdev.soulparkingtest.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mutdev.soulparkingtest.domain.DeleteTodoUseCase
import com.mutdev.soulparkingtest.domain.InsertTodoUseCase
import com.mutdev.soulparkingtest.domain.LoadTodoListUseCase
import com.mutdev.soulparkingtest.domain.Todo
import com.mutdev.soulparkingtest.domain.UpdateTodoUseCase
import java.util.Date

class TodoListViewModel(
    private val load: LoadTodoListUseCase,
    private val insert: InsertTodoUseCase,
    private val update: UpdateTodoUseCase,
    private val delete: DeleteTodoUseCase,
): ViewModel() {


    private val _validationErrors = MutableLiveData<Map<String, String>>()
    val validationErrors: LiveData<Map<String, String>> get() = _validationErrors

    fun validateTodo(title: String, date: Date?): Boolean {
        val errors = mutableMapOf<String, String>()

        if (title.isEmpty()) {
            errors["title"] = "Title cannot be empty."
        }

        if (date == null) {
            errors["date"] = "Please select a date and time."
        }

        if (errors.isNotEmpty()) {
            _validationErrors.value = errors
            return false
        }

        return true
    }

    fun getTodos() = load().map { todoList ->
        todoList.sortedBy { todo ->
            todo.date
        }
    }

    suspend fun insertTodo(todo: Todo) = insert(todo)

    suspend fun updateTodo(todo: Todo) = update(todo)

    suspend fun deleteTodo(todo: Todo) = delete(todo)
}