package com.mutdev.soulparkingtest.domain

interface InsertTodoUseCase {
    suspend operator fun invoke(todo: Todo): Long
}