package com.example.plantpal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.plantpal.MyGardenRepository
import com.example.plantpal.plants.Plant
import kotlinx.coroutines.launch

class PlantViewModel(private val repository: MyGardenRepository) : ViewModel() {

    fun addPlantToGarden(plant: Plant) = viewModelScope.launch {
        repository.addPlant(plant)
    }
}

class PlantViewModelFactory(private val repository: MyGardenRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
