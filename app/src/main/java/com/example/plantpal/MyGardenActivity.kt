package com.example.plantpal

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.plantpal.plants.Plant
import com.example.plantpal.plants.PlantDetailsActivity
import com.example.plantpal.profile.ProfileActivity
import com.example.plantpal.viewmodels.MyGardenViewModel
import com.example.plantpal.viewmodels.MyGardenViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyGardenActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var adapter: PlantGridAdapter
    private val plantList = mutableListOf<Plant>()

    private val myGardenViewModel: MyGardenViewModel by viewModels {
        MyGardenViewModelFactory((application as PlantPalApplication).gardenRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_garden)

        gridView = findViewById(R.id.plant_grid)
        adapter = PlantGridAdapter(this, plantList) { plant ->
            myGardenViewModel.removePlant(plant)
        }
        gridView.adapter = adapter

        myGardenViewModel.myGardenPlants.observe(this) { plants ->
            plantList.clear()
            plantList.addAll(plants)
            adapter.notifyDataSetChanged()
        }

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
}
