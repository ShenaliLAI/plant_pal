package com.example.plantpal

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.plantpal.plants.PlantDetailsActivity
import com.example.plantpal.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyGardenActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var adapter: PlantGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_garden)

        gridView = findViewById(R.id.plant_grid)

        val plantList = MyGardenRepository.getMyGardenPlants()

        adapter = PlantGridAdapter(this, plantList)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val selectedPlant = plantList[position]
            val intent = Intent(this, PlantDetailsActivity::class.java)
            intent.putExtra("PLANT_EXTRA", selectedPlant)
            startActivity(intent)
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.nav_my_garden
        val navigationHandler = BottomNavigationHandler(this, bottomNavigation)
        navigationHandler.setup()

        findViewById<android.widget.ImageView>(R.id.profile_icon).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the grid view in case a plant was added or removed
        adapter.notifyDataSetChanged()
    }
}
