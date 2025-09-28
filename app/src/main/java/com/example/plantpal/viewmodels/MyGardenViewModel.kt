package com.example.plantpal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.plantpal.MyGardenRepository
import com.example.plantpal.plants.Plant
import kotlinx.coroutines.launch

class MyGardenViewModel(private val repository: MyGardenRepository) : ViewModel() {

    val myGardenPlants: LiveData<List<Plant>> = repository.getMyGardenPlants().asLiveData()

    fun addPlant(plant: Plant) = viewModelScope.launch {
        repository.addPlant(plant)
    }

    fun removePlant(plant: Plant) = viewModelScope.launch {
        repository.removePlant(plant)
    }
}

class MyGardenViewModelFactory(private val repository: MyGardenRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyGardenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyGardenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
