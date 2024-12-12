package com.mutdev.soulparkingtest.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mutdev.soulparkingtest.domain.DeleteTodoUseCase
import com.mutdev.soulparkingtest.domain.InsertTodoUseCase
import com.mutdev.soulparkingtest.domain.LoadTodoListUseCase
import com.mutdev.soulparkingtest.domain.UpdateTodoUseCase

class TodoListViewModelFactory(
    private val load: LoadTodoListUseCase,
    private val insert: InsertTodoUseCase,
    private val update: UpdateTodoUseCase,
    private val delete: DeleteTodoUseCase
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoListViewModel(
            load,
            insert,
            update,
            delete
        ) as T
    }
}