package com.mutdev.soulparkingtest.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class UpdateTodoUseCaseTest {
    @Test
    fun shouldUpdateTodo() = runBlocking {
        val updateTodoUseCase = mockk<UpdateTodoUseCase>()
        val expectedResult = 1
        val todo = Todo(id = 1, "Test Todo", "Description", Date())
        coEvery { updateTodoUseCase(any()) } returns expectedResult

        val result = updateTodoUseCase(todo.copy(title = "Updated Test Todo", date = Date()))
        assertEquals(expectedResult, result)
    }
}

