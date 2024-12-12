package com.mutdev.soulparkingtest.domain

interface DeleteTodoUseCase {
    suspend operator fun invoke(todo: Todo): Int
}