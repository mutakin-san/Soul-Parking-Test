package com.mutdev.soulparkingtest.data

import com.mutdev.soulparkingtest.data.usecase.InsertLocalTodoUseCase
import com.mutdev.soulparkingtest.data.usecase.UpdateLocalTodoUseCase
import com.mutdev.soulparkingtest.domain.Todo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class UpdateLocalTodoUseCaseTest: LocalDatabaseHelper() {

    @Test
    fun shouldUpdateTodoInLocalDatabase() = runBlocking {
        val updateLocalTodoUseCase = UpdateLocalTodoUseCase(dao)
        val insertLocalTodoUseCase = InsertLocalTodoUseCase(dao)

        val todo = Todo(id = 1, "Test Todo", "Description", Date())
        val expectedResult = 1
        insertLocalTodoUseCase(todo)

        val updatedTodo = todo.copy(title = "Updated Test Todo", date = Date())

        val result = updateLocalTodoUseCase(updatedTodo)
        assertEquals(expectedResult, result)
    }
}
