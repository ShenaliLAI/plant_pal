package com.example.plantpal.growth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.plantpal.R
import com.example.plantpal.BottomNavigationHandler
import com.example.plantpal.profile.ProfileActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GrowthTrackerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_growth_tracker)

        // Setup Plants RecyclerView
        val plantsRecyclerView = findViewById<RecyclerView>(R.id.plants_recycler_view)
        plantsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val plants = listOf(
            Plant("Rose", R.drawable.rose_f),
            Plant("Sunflower", R.drawable.sun_f),
            Plant("Lavender", R.drawable.lavender_f),
            Plant("Marigold", R.drawable.marigold_f),
            Plant("Tulip", R.drawable.tulip_f),
            Plant("Orchid", R.drawable.orchid_f)
        )
        plantsRecyclerView.adapter = PlantAdapter(plants) { plant ->
            updateTimeline(plant)
        }

        // Setup Timeline RecyclerView
        val timelineRecyclerView = findViewById<RecyclerView>(R.id.timeline_recycler_view)
        timelineRecyclerView.layoutManager = LinearLayoutManager(this)
        // Check for incoming plant name
        val plantNameToDisplay = intent.getStringExtra("PLANT_NAME")
        val plantToDisplay = plants.find { it.name == plantNameToDisplay } ?: plants.first()
        updateTimeline(plantToDisplay)

        // Setup Bottom Navigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.nav_growth_tracker // Set the current item as selected
        val navigationHandler = BottomNavigationHandler(this, bottomNavigation)
        navigationHandler.setup()

        val addNoteFab = findViewById<FloatingActionButton>(R.id.add_note_fab)
        addNoteFab.setOnClickListener {
            val intent = Intent(this, AddGrowthNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        val profileIcon = findViewById<android.widget.ImageView>(R.id.profile_icon)
        profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun updateTimeline(plant: Plant) {
        val timelineTitle = findViewById<android.widget.TextView>(R.id.timeline_title)
        timelineTitle.text = "${plant.name}’s timeline"

        val timelineRecyclerView = findViewById<RecyclerView>(R.id.timeline_recycler_view)
        val timelineEntries = getTimelineForPlant(plant.name)
        timelineRecyclerView.adapter = TimelineAdapter(timelineEntries)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            val note = data?.getStringExtra("note")
            val imageUri = data?.getStringExtra("imageUri")
            val newEntry = TimelineEntry("Sep, 10", note ?: "", "", imageUri)
            timelineEntries.add(0, newEntry)
            (findViewById<RecyclerView>(R.id.timeline_recycler_view).adapter)?.notifyDataSetChanged()
        }
    }

    private val timelineEntries = mutableListOf<TimelineEntry>()

    private fun getTimelineForPlant(plantName: String): List<TimelineEntry> {
        timelineEntries.clear()
        val entries = when (plantName) {
            "Rose" -> listOf(
                TimelineEntry("Sep, 01", "First rose bud appeared.", "Height: 30cm"),
                TimelineEntry("Aug, 25", "Pruned the lower branches.", "Height: 28cm")
            )
            "Sunflower" -> listOf(
                TimelineEntry("Jul, 30", "The sunflower head is starting to track the sun.", "Height: 150cm"),
                TimelineEntry("Jul, 20", "Reached a new height milestone.", "Height: 120cm")
            )
            "Lavender" -> listOf(
                TimelineEntry("Jun, 15", "Harvested some lavender for drying.", "Height: 40cm"),
                TimelineEntry("Jun, 01", "Beautiful purple flowers are in full bloom.", "Height: 38cm")
            )
            "Marigold" -> listOf(
                TimelineEntry("Aug, 10", "Deadheaded spent flowers to encourage more blooms.", "Height: 25cm"),
                TimelineEntry("Jul, 28", "Vibrant orange and yellow flowers are attracting bees.", "Height: 22cm")
            )
            "Tulip" -> listOf(
                TimelineEntry("Apr, 20", "Tulips have bloomed with beautiful red petals.", "Height: 35cm"),
                TimelineEntry("Apr, 10", "The bulbs have sprouted and are growing quickly.", "Height: 15cm")
            )
            "Orchid" -> listOf(
                TimelineEntry("Sep, 05", "The orchid is in full bloom.", "Height: 25cm"),
                TimelineEntry("Aug, 20", "Watered with orchid fertilizer.", "Height: 24cm")
            )
            else -> emptyList()
        }
        timelineEntries.addAll(entries)
        return timelineEntries
    }

    companion object {
        private const val ADD_NOTE_REQUEST = 1
    }
}
