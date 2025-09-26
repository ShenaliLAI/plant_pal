package com.example.plantpal

import android.app.Activity
import android.content.Intent
import com.example.plantpal.home.HomeActivity
import com.example.plantpal.reminders.RemindersActivity
import com.example.plantpal.growth.GrowthTrackerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHandler(private val activity: Activity, private val view: BottomNavigationView) {

    fun setup() {
        view.setOnItemSelectedListener {
            val intent: Intent? = when (it.itemId) {
                R.id.nav_plant_library -> Intent(activity, HomeActivity::class.java)
                R.id.nav_my_garden -> Intent(activity, MyGardenActivity::class.java)
                R.id.nav_reminders -> Intent(activity, RemindersActivity::class.java)
                R.id.nav_growth_tracker -> Intent(activity, GrowthTrackerActivity::class.java)
                else -> null
            }

            intent?.let {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                activity.startActivity(it)
            }

            true // Return true to show the item as selected
        }
    }
}
