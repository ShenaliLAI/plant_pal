package com.example.plantpal.reminders

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plantpal.R

class ReminderAdapter(
    private val onEditClick: (Reminder) -> Unit,
    private val onDeleteClick: (Reminder) -> Unit,
    private val onToggleComplete: (Reminder, Boolean) -> Unit
) : ListAdapter<Reminder, ReminderAdapter.ReminderViewHolder>(ReminderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = getItem(position)
        holder.bind(reminder, onEditClick, onDeleteClick, onToggleComplete)
    }

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.task_title)
        private val checkbox: CheckBox = itemView.findViewById(R.id.task_checkbox)
        private val editButton: ImageView = itemView.findViewById(R.id.edit_button)
        private val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)

        fun bind(
            reminder: Reminder,
            onEditClick: (Reminder) -> Unit,
            onDeleteClick: (Reminder) -> Unit,
            onToggleComplete: (Reminder, Boolean) -> Unit
        ) {
            titleView.text = reminder.title
            checkbox.isChecked = reminder.isCompleted

            updateStrikeThrough(titleView, reminder.isCompleted)

            editButton.setOnClickListener { onEditClick(reminder) }
            deleteButton.setOnClickListener { onDeleteClick(reminder) }

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                onToggleComplete(reminder, isChecked)
            }
        }

        private fun updateStrikeThrough(textView: TextView, isCompleted: Boolean) {
            if (isCompleted) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
}

class ReminderDiffCallback : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem == newItem
    }
}
