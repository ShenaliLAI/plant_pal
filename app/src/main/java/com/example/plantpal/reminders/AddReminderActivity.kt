package com.example.plantpal.reminders

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.text.SimpleDateFormat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.plantpal.R
import com.example.plantpal.profile.ProfileActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Calendar

class AddReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        val saveButton = findViewById<Button>(R.id.save_button)
        val taskTypeChipGroup = findViewById<ChipGroup>(R.id.task_type_chip_group)
        val plantNameEditText = findViewById<EditText>(R.id.plant_name_edit_text)
        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener {
            finish()
        }

        val profileIcon = findViewById<ImageView>(R.id.profile_icon)
        profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        val dateEditText = findViewById<TextView>(R.id.date_edit_text)
        dateEditText.setOnClickListener {
            showDatePickerDialog()
        }
        val timeEditText = findViewById<TextView>(R.id.time_edit_text)
        timeEditText.setOnClickListener {
            showTimePickerDialog()
        }
        val titleTextView = findViewById<TextView>(R.id.add_reminder_title)

        val isEditMode = intent.getBooleanExtra("is_edit", false)
        val taskId = intent.getIntExtra("task_id", -1)
        val taskTitle = intent.getStringExtra("task_title")
        val plantNameFromGarden = intent.getStringExtra("plant_name")

        if (isEditMode) {
            titleTextView.text = "Edit Reminder"
            plantNameEditText.setText(taskTitle)
            // Optionally disable task type selection during edit
            taskTypeChipGroup.visibility = View.GONE
        } else if (plantNameFromGarden != null) {
            plantNameEditText.setText(plantNameFromGarden)
            taskTypeChipGroup.visibility = View.VISIBLE // Ensure it's visible if coming from garden
        }

        saveButton.setOnClickListener {
            val selectedChipId = taskTypeChipGroup.checkedChipId
            val selectedChip = findViewById<Chip>(selectedChipId)
            val taskType = selectedChip?.text.toString()

            val plantName = plantNameEditText.text.toString()
            val date = dateEditText.text.toString()
            val time = timeEditText.text.toString()

                        val resultIntent = Intent()
            if (isEditMode) {
                resultIntent.putExtra("task_id", taskId)
                resultIntent.putExtra("task_title", plantName) // The edited text is in plantNameEditText
            } else {
                resultIntent.putExtra("task_type", taskType)
                resultIntent.putExtra("plant_name", plantName)
                resultIntent.putExtra("date", date)
                resultIntent.putExtra("time", time)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            val dateEditText = findViewById<TextView>(R.id.date_edit_text)
            dateEditText.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
            selectedTime.set(Calendar.MINUTE, selectedMinute)
            val timeFormat = SimpleDateFormat("h:mm a")
            val formattedTime = timeFormat.format(selectedTime.time)

            val timeEditText = findViewById<TextView>(R.id.time_edit_text)
            timeEditText.text = formattedTime
        }, hour, minute, false)

        timePickerDialog.show()
    }
}
