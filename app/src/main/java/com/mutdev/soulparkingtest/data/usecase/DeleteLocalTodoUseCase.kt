package com.mutdev.soulparkingtest.data.usecase

import com.mutdev.soulparkingtest.data.TodoDao
import com.mutdev.soulparkingtest.domain.DeleteTodoUseCase
import com.mutdev.soulparkingtest.domain.Todo

class DeleteLocalTodoUseCase(private val dao: TodoDao): DeleteTodoUseCase {
    override suspend fun invoke(todo: Todo): Int {
        return dao.deleteTodo(todo.toEntity())
    }
}
