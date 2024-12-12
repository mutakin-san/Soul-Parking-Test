package com.mutdev.soulparkingtest.domain

import androidx.lifecycle.liveData
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class LoadTodoListUseCaseTest {
    @Test
    fun shouldLoadTodoList() {
        val loadTodoListUseCase = mockk<LoadTodoListUseCase>()
        val expectedResult = liveData<List<Todo>> { listOf(Todo(id = 1, "Test Todo", "Description", Date())) }
        every { loadTodoListUseCase() } returns expectedResult

        assertEquals(expectedResult, loadTodoListUseCase())
    }
}