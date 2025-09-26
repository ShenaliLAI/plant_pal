package com.example.plantpal.plants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.plantpal.R
import com.example.plantpal.profile.ProfileActivity
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import com.example.plantpal.BottomNavigationHandler
import com.google.android.material.bottomnavigation.BottomNavigationView

class FloweringPlantsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flowering_plants)

        val recyclerView = findViewById<RecyclerView>(R.id.plants_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val plantList = listOf(
            Plant("Rose", "Rosa Rubiginosa", "Beautiful fragrant roses perfect for gardens", "Every 2-3 days", "Full sun", "60° - 70° F", R.drawable.rose_f),
            Plant("Sun Flower", "Helianthus", "Tall, bright flowers that follow the sun", "Every 3-4 days", "Full sun", "70° - 80° F", R.drawable.sun_f),
            Plant("Lavender", "Lavandula", "Aromatic purple flowers with calming properties", "Every 5-7 days", "Full sun", "65° - 85° F", R.drawable.lavender_f),
            Plant("Marigold", "Tagetes", "Hardy, vibrant flowers that deter pests", "Every 4-5 days", "Full sun", "60° - 75° F", R.drawable.marigold_f),
            Plant("Tulip", "Tulipa", "Colorful bulbous flowers that bloom in spring", "Every 3-5 days", "Full sun", "55° - 65° F", R.drawable.tulip_f),
            Plant("Orchid", "Orchidaceae", "Exotic and elegant flowers with unique shapes", "Every 7-10 days", "Partial shade", "65° - 80° F", R.drawable.orchid_f)
        )

        val adapter = PlantAdapter(plantList)
        recyclerView.adapter = adapter

        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener {
            finish() // Go back to the previous activity
        }

        val profileIcon = findViewById<ImageView>(R.id.profile_icon)
        profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navigationHandler = BottomNavigationHandler(this, bottomNavigation)
        navigationHandler.setup()
    }
}
