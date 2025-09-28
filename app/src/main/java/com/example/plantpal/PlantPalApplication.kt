package com.example.plantpal

import android.app.Application
import com.example.plantpal.data.AppDatabase
import com.example.plantpal.data.ReminderRepository

class PlantPalApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val gardenRepository by lazy { MyGardenRepository(database.plantDao()) }
    val reminderRepository by lazy { ReminderRepository(database.reminderDao()) }
}
