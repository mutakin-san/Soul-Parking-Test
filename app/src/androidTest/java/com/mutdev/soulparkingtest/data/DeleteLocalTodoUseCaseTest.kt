package com.mutdev.soulparkingtest.data

import com.mutdev.soulparkingtest.data.usecase.DeleteLocalTodoUseCase
import com.mutdev.soulparkingtest.data.usecase.InsertLocalTodoUseCase
import com.mutdev.soulparkingtest.domain.Todo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class DeleteLocalTodoUseCaseTest: LocalDatabaseHelper() {

    @Test
    fun shouldDeleteTodoInLocalDatabase() = runBlocking {
        val deleteLocalTodoUseCase = DeleteLocalTodoUseCase(dao)
        val insertLocalTodoUseCase = InsertLocalTodoUseCase(dao)

        val todo = Todo(id = 1, "Test Todo", "Description", Date())
        val expectedResult = 1
        insertLocalTodoUseCase(todo)

        val result = deleteLocalTodoUseCase(todo)
        assertEquals(expectedResult, result)
    }
}
