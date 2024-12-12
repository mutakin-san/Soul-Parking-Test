package com.mutdev.soulparkingtest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class LocalDatabaseHelper {
     private lateinit var db: TodoDatabase
     lateinit var dao: TodoDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoDatabase::class.java
        ).allowMainThreadQueries()
            .build()


        dao = db.todoDao()
    }

    @After
    fun closeDb() {
        db.close()
    }
}