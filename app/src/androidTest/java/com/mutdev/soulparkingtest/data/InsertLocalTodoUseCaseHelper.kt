package com.mutdev.soulparkingtest.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mutdev.soulparkingtest.data.usecase.InsertLocalTodoUseCase
import com.mutdev.soulparkingtest.domain.Todo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class InsertLocalTodoUseCaseHelper: LocalDatabaseHelper() {

    @Test
    fun shouldInsertTodoToLocalDatabase() = runBlocking {
        val insertLocalTodoUseCase = InsertLocalTodoUseCase(dao)
        val todo = Todo(title = "Test Todo", description =  "Description", date =  Date())

        assertEquals(1L, insertLocalTodoUseCase(todo))
        assertEquals(2L, insertLocalTodoUseCase(todo))
        assertEquals(3L, insertLocalTodoUseCase(todo))
    }

}