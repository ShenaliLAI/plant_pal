package com.example.plantpal.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.plantpal.BottomNavigationHandler
import com.example.plantpal.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        // Note: There is no profile item in the nav menu, so we don't set a selected item.
        val navigationHandler = BottomNavigationHandler(this, bottomNavigation)
        navigationHandler.setup()
    }
}
