package com.mutdev.soulparkingtest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String = "",
    val dateInMillis: Long,
    val isDone: Boolean = false
)