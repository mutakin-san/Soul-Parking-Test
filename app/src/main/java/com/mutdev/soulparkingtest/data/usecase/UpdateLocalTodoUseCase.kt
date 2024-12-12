package com.mutdev.soulparkingtest.data.usecase

import com.mutdev.soulparkingtest.data.TodoDao
import com.mutdev.soulparkingtest.domain.Todo
import com.mutdev.soulparkingtest.domain.UpdateTodoUseCase

class UpdateLocalTodoUseCase(private val dao: TodoDao): UpdateTodoUseCase {
    override suspend fun invoke(todo: Todo): Int {
        return dao.updateTodo(todo.toEntity())
    }
}
