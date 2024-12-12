package com.mutdev.soulparkingtest.data.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mutdev.soulparkingtest.data.TodoDao
import com.mutdev.soulparkingtest.domain.LoadTodoListUseCase
import com.mutdev.soulparkingtest.domain.Todo

class LoadLocalTodoListUseCase(private val store: TodoDao): LoadTodoListUseCase {
    override fun invoke(): LiveData<List<Todo>> {
        return store.getTodos().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }
}