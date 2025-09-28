package com.example.plantpal.reminders

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.plantpal.R
import com.example.plantpal.BottomNavigationHandler
import com.example.plantpal.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONException

class RemindersActivity : AppCompatActivity() {

    // Request codes
    private val ADD_TASK_REQUEST = 1
    private val EDIT_TASK_REQUEST = 2

    private val viewIdToTaskIdMap = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

//        fetchWeather()
        setupBottomNavigation()

        val addReminderButton = findViewById<Button>(R.id.add_reminder_button)
        addReminderButton.setOnClickListener {
            val intent = Intent(this, AddReminderActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }

        // Setup listeners for hardcoded tasks
        setupTaskListeners(findViewById(R.id.task_1_card), 1)
        setupTaskListeners(findViewById(R.id.task_2_card), 2)
        setupTaskListeners(findViewById(R.id.task_3_card), 3)
    }

    private fun setupTaskListeners(cardView: View, taskId: Int) {
        val titleView = cardView.findViewById<TextView>(R.id.task_1_title) ?: cardView.findViewById(R.id.task_2_title) ?: cardView.findViewById(R.id.task_3_title) ?: cardView.findViewById(R.id.task_title)
        val checkbox = cardView.findViewById<CheckBox>(R.id.task_1_checkbox) ?: cardView.findViewById<CheckBox>(R.id.task_2_checkbox) ?: cardView.findViewById<CheckBox>(R.id.task_3_checkbox) ?: cardView.findViewById<CheckBox>(R.id.task_checkbox)
        val editButton = cardView.findViewById<ImageView>(R.id.task_1_edit) ?: cardView.findViewById<ImageView>(R.id.task_2_edit) ?: cardView.findViewById<ImageView>(R.id.task_3_edit) ?: cardView.findViewById<ImageView>(R.id.edit_button)
        val deleteButton = cardView.findViewById<ImageView>(R.id.task_1_delete) ?: cardView.findViewById<ImageView>(R.id.task_2_delete) ?: cardView.findViewById<ImageView>(R.id.task_3_delete) ?: cardView.findViewById<ImageView>(R.id.delete_button)

        checkbox?.setOnCheckedChangeListener { _, isChecked ->
            titleView.paintFlags = if (isChecked) {
                titleView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                titleView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        editButton?.setOnClickListener {
            val intent = Intent(this, AddReminderActivity::class.java).apply {
                putExtra("is_edit", true)
                putExtra("task_id", taskId)
                putExtra("task_title", titleView.text.toString())
            }
            startActivityForResult(intent, EDIT_TASK_REQUEST)
        }

        deleteButton?.setOnClickListener {
            (cardView.parent as? LinearLayout)?.removeView(cardView)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_TASK_REQUEST -> {
                    val taskType = data?.getStringExtra("task_type")
                    val plantName = data?.getStringExtra("plant_name")
                    val taskText = if (taskType.isNullOrEmpty() || taskType == "null") plantName else "$taskType $plantName"

                    val remindersContainer = findViewById<LinearLayout>(R.id.reminders_container)
                    val inflater = LayoutInflater.from(this)
                    val newReminderView = inflater.inflate(R.layout.item_reminder, remindersContainer, false)

                    val taskTitle = newReminderView.findViewById<TextView>(R.id.task_title)
                    taskTitle.text = taskText

                    val newTaskId = View.generateViewId()
                    newReminderView.id = newTaskId
                    viewIdToTaskIdMap[newTaskId] = newTaskId // Store mapping
                    setupTaskListeners(newReminderView, newTaskId)

                    remindersContainer.addView(newReminderView, 0)
                }
                EDIT_TASK_REQUEST -> {
                    val taskId = data?.getIntExtra("task_id", -1)
                    val updatedTitle = data?.getStringExtra("task_title")

                    if (taskId != null && taskId != -1 && updatedTitle != null) {
                        val viewId = viewIdToTaskIdMap.entries.find { it.value == taskId }?.key ?: taskId
                        val viewToUpdate = findViewById<View>(viewId)
                        viewToUpdate?.let {
                            val title = it.findViewById<TextView>(R.id.task_1_title) ?: it.findViewById<TextView>(R.id.task_2_title) ?: it.findViewById<TextView>(R.id.task_3_title) ?: it.findViewById<TextView>(R.id.task_title)
                            title?.text = updatedTitle
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

    /*private fun fetchWeather() {
        val textViewTemp = findViewById<TextView>(R.id.temperature_text)
        val textViewCondition = findViewById<TextView>(R.id.weather_condition_text)
        val apiKey = "YOUR_API_KEY" // IMPORTANT: Replace with your actual API key
        val city = "Galle"
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"

        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val main = response.getJSONObject("main")
                    val weather = response.getJSONArray("weather").getJSONObject(0)
                    val temp = main.getString("temp") + "° C"
                    val condition = weather.getString("description")
                    textViewTemp.text = temp
                    textViewCondition.text = condition.replaceFirstChar { it.titlecase() }
                } catch (e: JSONException) {
                    textViewTemp.text = "N/A"
                    textViewCondition.text = "Error"
                }
            },
            { _ ->
                textViewTemp.text = "N/A"
                textViewCondition.text = "Error"
            }
        )
        queue.add(jsonObjectRequest)
    }*/
}
