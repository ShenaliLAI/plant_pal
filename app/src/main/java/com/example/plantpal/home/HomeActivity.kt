package com.example.plantpal.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.plantpal.plants.FloweringPlantsActivity
import androidx.cardview.widget.CardView
import com.example.plantpal.BottomNavigationHandler
import com.example.plantpal.R
import com.example.plantpal.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val floweringPlantsCard = findViewById<CardView>(R.id.card_flowering_plants)
        floweringPlantsCard.setOnClickListener {
            val intent = Intent(this, FloweringPlantsActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.nav_plant_library
        val navigationHandler = BottomNavigationHandler(this, bottomNavigation)
        navigationHandler.setup()

        val profileIcon = findViewById<android.widget.ImageView>(R.id.profile_icon)
        profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
