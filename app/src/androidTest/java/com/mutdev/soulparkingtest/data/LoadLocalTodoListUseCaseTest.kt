package com.mutdev.soulparkingtest.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mutdev.soulparkingtest.data.usecase.InsertLocalTodoUseCase
import com.mutdev.soulparkingtest.data.usecase.LoadLocalTodoListUseCase
import com.mutdev.soulparkingtest.domain.Todo
import com.mutdev.soulparkingtest.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class LoadLocalTodoListUseCaseTest: LocalDatabaseHelper() {

    @Test
    fun shouldLoadTodoListFromLocalDatabase() = runBlocking{
        val loadLocalTodoListUseCase = LoadLocalTodoListUseCase(dao)
        val insertLocalTodoUseCase = InsertLocalTodoUseCase(dao)

        val todo = Todo(id = 1, "Test Todo", "Description", Date())
        insertLocalTodoUseCase(todo)

        val expectedResult = listOf(todo)

        assertEquals(expectedResult, loadLocalTodoListUseCase().getOrAwaitValue())
    }
}