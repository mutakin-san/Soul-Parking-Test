package com.mutdev.soulparkingtest.data.usecase

import com.mutdev.soulparkingtest.data.TodoEntity
import com.mutdev.soulparkingtest.domain.Todo
import java.util.Date

fun Todo.toEntity(): TodoEntity = TodoEntity(id = id, title = title, description =  description, dateInMillis = date.time, isDone = isDone)

fun TodoEntity.toDomain(): Todo = Todo(id = id, title = title, description =  description, date = Date(dateInMillis), isDone = isDone)