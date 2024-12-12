package com.mutdev.soulparkingtest.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class InsertTodoUseCaseUseCaseTest {
    @Test
    fun shouldInsertTodo() = runBlocking {
        val insertTodoUseCase = mockk<InsertTodoUseCase>()
        val expectedResult = 1L
        val todo = Todo(title ="Test Todo", description =  "Description", date = Date())
        coEvery { insertTodoUseCase(todo) } returns expectedResult

        val result = insertTodoUseCase(todo)
        assertEquals(expectedResult, result)
    }
}

