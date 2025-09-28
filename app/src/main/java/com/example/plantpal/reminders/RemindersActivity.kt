package com.example.plantpal.reminders

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantpal.PlantPalApplication
import com.example.plantpal.R
import com.example.plantpal.BottomNavigationHandler
import com.example.plantpal.profile.ProfileActivity
import com.example.plantpal.viewmodels.ReminderViewModel
import com.example.plantpal.viewmodels.ReminderViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class RemindersActivity : AppCompatActivity() {

    private val ADD_TASK_REQUEST = 1
    private val EDIT_TASK_REQUEST = 2

    private val reminderViewModel: ReminderViewModel by viewModels {
        ReminderViewModelFactory((application as PlantPalApplication).reminderRepository)
    }

    private lateinit var reminderAdapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        setupBottomNavigation()

        val addReminderButton = findViewById<Button>(R.id.add_reminder_button)
        addReminderButton.setOnClickListener {
            val intent = Intent(this, AddReminderActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }

        setupRecyclerView()

        reminderViewModel.allReminders.observe(this) { reminders ->
            reminders?.let { reminderAdapter.submitList(it) }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.reminders_recycler_view)
        reminderAdapter = ReminderAdapter(
            onEditClick = { reminder ->
                val intent = Intent(this, AddReminderActivity::class.java).apply {
                    putExtra("is_edit", true)
                    putExtra("task_id", reminder.id)
                    putExtra("task_title", reminder.title)
                }
                startActivityForResult(intent, EDIT_TASK_REQUEST)
            },
            onDeleteClick = { reminder ->
                reminderViewModel.deleteReminder(reminder)
            },
            onToggleComplete = { reminder, isCompleted ->
                reminderViewModel.updateReminder(reminder.copy(isCompleted = isCompleted))
            }
        )
        recyclerView.adapter = reminderAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val taskType = data?.getStringExtra("task_type")
            val plantName = data?.getStringExtra("plant_name")
            val taskText = if (taskType.isNullOrEmpty() || taskType == "null") plantName else "$taskType $plantName"

            if (taskText != null) {
                when (requestCode) {
                    ADD_TASK_REQUEST -> {
                        val newReminder = Reminder(title = taskText)
                        reminderViewModel.addReminder(newReminder)
                    }
                    EDIT_TASK_REQUEST -> {
                        val taskId = data?.getIntExtra("task_id", -1)
                        if (taskId != null && taskId != -1) {
                            val updatedReminder = Reminder(id = taskId, title = taskText)
                            reminderViewModel.updateReminder(updatedReminder)
                        }
                    }
                }
            }
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.nav_reminders
        val navigationHandler = BottomNavigationHandler(this, bottomNavigation)
        navigationHandler.setup()

        val profileIcon = findViewById<android.widget.ImageView>(R.id.profile_icon)
        profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}

