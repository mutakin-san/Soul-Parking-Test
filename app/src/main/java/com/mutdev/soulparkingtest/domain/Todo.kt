package com.mutdev.soulparkingtest.domain

import java.util.Date

data class Todo(
    val id: Int? = null,
    val title: String,
    val description: String = "",
    val date: Date,
    val isDone: Boolean = false
)
