package com.mutdev.soulparkingtest.domain

interface UpdateTodoUseCase {
    suspend operator fun invoke(todo: Todo): Int
}