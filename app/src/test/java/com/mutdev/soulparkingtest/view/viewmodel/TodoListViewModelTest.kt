package com.mutdev.soulparkingtest.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mutdev.soulparkingtest.data.usecase.LoadLocalTodoListUseCase
import com.mutdev.soulparkingtest.domain.DeleteTodoUseCase
import com.mutdev.soulparkingtest.domain.InsertTodoUseCase
import com.mutdev.soulparkingtest.domain.Todo
import com.mutdev.soulparkingtest.domain.UpdateTodoUseCase
import com.mutdev.soulparkingtest.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

class TodoListViewModelTest {

    private lateinit var todoListViewModel: TodoListViewModel
    private val todoList = mutableListOf(
        Todo(1, "Test 1", "", Date()),
        Todo(2, "Test 2", "", Date()),
        Todo(3, "Test 3", "", Date()),
    )
    private val todo = Todo(1, "Test 1", "", Date())


    @Before
    fun setup() {
        val loadDataUseCase = mockk<LoadLocalTodoListUseCase>()
        val insertUseCase = mockk<InsertTodoUseCase>()
        val updateUseCase = mockk<UpdateTodoUseCase>()
        val deleteUseCase = mockk<DeleteTodoUseCase>()

        every { loadDataUseCase() } returns MutableLiveData(todoList)
        coEvery { insertUseCase(todo) } returns todo.id!!.toLong()
        coEvery { updateUseCase(todo.copy(title = "Test 1 Updated")) } returns 1
        coEvery { deleteUseCase(todo) } returns 1

        todoListViewModel = TodoListViewModel(loadDataUseCase, insertUseCase, updateUseCase, deleteUseCase)

    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun shouldSuccessfullyLoadData() {
        val result = todoListViewModel.getTodos().getOrAwaitValue()

        assertTrue(result.isNotEmpty())
        assertEquals(todoList, result)
    }


    @Test
    fun shouldSuccessfullyInsertData() = runBlocking {
        assertEquals(1L, todoListViewModel.insertTodo(todo))
    }

    @Test
    fun shouldSuccessfullyUpdateData() = runBlocking {
        assertEquals(1, todoListViewModel.updateTodo(todo.copy(title = "Test 1 Updated")))
    }

    @Test
    fun shouldSuccessfullyDeleteData() = runBlocking {
        assertEquals(1, todoListViewModel.deleteTodo(todo))
    }

    @Test
    fun shouldValidateTodo() {
        val expectedErrors = mapOf(
            "title" to "Title cannot be empty.",
            "date" to "Please select a date and time."
        )
        assertEquals(false, todoListViewModel.validateTodo("", null))
        val validationErrors = todoListViewModel.validationErrors.getOrAwaitValue()
        assertEquals(expectedErrors, validationErrors)
    }


}