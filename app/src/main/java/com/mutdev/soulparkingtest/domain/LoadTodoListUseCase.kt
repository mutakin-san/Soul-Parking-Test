package com.mutdev.soulparkingtest.domain

import androidx.lifecycle.LiveData

interface LoadTodoListUseCase {
    operator fun invoke(): LiveData<List<Todo>>
}