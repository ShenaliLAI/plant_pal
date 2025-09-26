package com.example.plantpal.plants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plant(
    val name: String,
    val scientificName: String,
    val description: String,
    val watering: String,
    val sunlight: String,
    val temperature: String,
    val imageResId: Int
) : Parcelable
