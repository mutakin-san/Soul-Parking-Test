package com.mutdev.soulparkingtest.data.usecase

import com.mutdev.soulparkingtest.data.TodoDao
import com.mutdev.soulparkingtest.domain.InsertTodoUseCase
import com.mutdev.soulparkingtest.domain.Todo

class InsertLocalTodoUseCase(private val store: TodoDao): InsertTodoUseCase {
    override suspend fun invoke(todo: Todo): Long {
        return store.insertTodo(todo.toEntity())
    }
}