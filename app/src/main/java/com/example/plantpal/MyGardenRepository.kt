package com.example.plantpal

import com.example.plantpal.plants.Plant

object MyGardenRepository {
    private val myGardenPlants = mutableListOf<Plant>()

    // Initialize with some default plants for demonstration
    init {
        if (myGardenPlants.isEmpty()) {
            myGardenPlants.add(Plant("Rose", "Rosa Rubiginosa", "Beautiful fragrant roses", "Every 2-3 days", "Full sun", "60° - 70° F", R.drawable.rose_f))
            myGardenPlants.add(Plant("Guava", "Psidium guajava", "Tropical fruit tree", "Every 4-5 days", "Full sun", "75° - 85° F", R.drawable.fruit_plants)) // Placeholder
            myGardenPlants.add(Plant("Cactus", "Cactaceae", "Drought-tolerant succulent", "Every 2-3 weeks", "Full sun", "70° - 90° F", R.drawable.cactus_plants))
            myGardenPlants.add(Plant("Carrot", "Daucus carota", "Root vegetable", "Every 3-4 days", "Full sun", "60° - 70° F", R.drawable.veg_plants)) // Placeholder
        }
    }

    fun getMyGardenPlants(): List<Plant> {
        return myGardenPlants
    }

    fun addPlant(plant: Plant) {
        // Prevent adding duplicate plants by checking the name
        if (myGardenPlants.none { it.name == plant.name }) {
            myGardenPlants.add(plant)
        }
    }

    fun removePlant(plant: Plant) {
        myGardenPlants.remove(plant)
    }
}
