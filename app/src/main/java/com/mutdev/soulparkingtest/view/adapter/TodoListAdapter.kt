package com.mutdev.soulparkingtest.view.adapter

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mutdev.soulparkingtest.databinding.TodoItemBinding
import com.mutdev.soulparkingtest.domain.Todo
import com.mutdev.soulparkingtest.view.AddUpdateTodoActivity
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

class TodoListAdapter(val onFinish: (Todo) -> Unit): ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(DIFF_CALLBACK) {

    inner class TodoViewHolder(private val binding: TodoItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            val formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, Locale.getDefault())
            val formattedDate = formatter.format(todo.date)

            with(binding) {
                tvTitle.text = todo.title
                if (todo.isDone) {
                    tvTitle.paint.isStrikeThruText = true
                }
                tvDescription.text = todo.description
                tvDate.text = formattedDate
                btnDone.isChecked = todo.isDone
                btnDone.setOnCheckedChangeListener { _, isChecked ->
                    tvTitle.paint.isStrikeThruText = isChecked
                    onFinish.invoke(todo.copy(isDone = isChecked))
                }

                root.setOnClickListener {
                    AddUpdateTodoActivity.moveIntent(root.context)
                        .putExtra(AddUpdateTodoActivity.EXTRA_ID, todo.id)
                        .putExtra(AddUpdateTodoActivity.EXTRA_TITLE, todo.title)
                        .putExtra(AddUpdateTodoActivity.EXTRA_DESCRIPTION, todo.description)
                        .putExtra(AddUpdateTodoActivity.EXTRA_DATE_IN_MILLIS, todo.date.time)
                        .let(root.context::startActivity)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}