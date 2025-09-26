package com.example.plantpal.plants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.content.Intent
import android.widget.TextView
import com.example.plantpal.R
import com.example.plantpal.BottomNavigationHandler
import com.google.android.material.bottomnavigation.BottomNavigationView

class PlantDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_details)

        val plant = intent.getParcelableExtra<Plant>("PLANT_EXTRA")

        if (plant != null) {
            findViewById<TextView>(R.id.plant_name_header).text = plant.name
            findViewById<TextView>(R.id.scientific_name_header).text = plant.scientificName
            findViewById<ImageView>(R.id.plant_image_details).setImageResource(plant.imageResId)
            findViewById<TextView>(R.id.plant_description_details).text = plant.description
            findViewById<TextView>(R.id.watering_info_details).text = plant.watering
            findViewById<TextView>(R.id.sunlight_info_details).text = plant.sunlight
            findViewById<TextView>(R.id.temperature_info_details).text = plant.temperature
        }

        findViewById<ImageView>(R.id.back_icon).setOnClickListener {
            finish()
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navigationHandler = BottomNavigationHandler(this, bottomNavigation)
        navigationHandler.setup()
    }
}
