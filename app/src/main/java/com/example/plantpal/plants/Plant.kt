package com.example.plantpal.plants

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val scientificName: String,
    val description: String,
    val watering: String,
    val sunlight: String,
    val temperature: String,
    val imageResId: Int
) : Parcelable
