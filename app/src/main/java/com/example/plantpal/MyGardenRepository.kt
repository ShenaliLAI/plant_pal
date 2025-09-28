package com.example.plantpal

import com.example.plantpal.data.PlantDao
import com.example.plantpal.plants.Plant
import kotlinx.coroutines.flow.Flow

class MyGardenRepository(private val plantDao: PlantDao) {

    fun getMyGardenPlants(): Flow<List<Plant>> = plantDao.getAllPlants()

    suspend fun addPlant(plant: Plant) {
        plantDao.insert(plant)
    }

    suspend fun removePlant(plant: Plant) {
        plantDao.delete(plant)
    }
}

