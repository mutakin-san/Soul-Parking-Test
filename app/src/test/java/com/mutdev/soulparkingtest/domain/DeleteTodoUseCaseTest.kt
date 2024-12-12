package com.mutdev.soulparkingtest.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class DeleteTodoUseCaseTest {
    @Test
    fun shouldDeleteTodo() = runBlocking {
        val deleteTodoUseCase = mockk<DeleteTodoUseCase>()
        val expectedResult = 1
        val todo = Todo(id = 1, "Test Todo", "Description", Date())
        coEvery { deleteTodoUseCase(any()) } returns expectedResult

        val result = deleteTodoUseCase(todo)
        assertEquals(expectedResult, result)
    }
}

