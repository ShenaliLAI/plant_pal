package com.example.plantpal.growth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantpal.R

data class Plant(val name: String, val imageResId: Int)

class PlantAdapter(private val plants: List<Plant>, private val onPlantSelected: (Plant) -> Unit) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant_growth, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.plantName.text = plant.name
        holder.plantImage.setImageResource(plant.imageResId)
        holder.itemView.setOnClickListener { onPlantSelected(plant) }
    }

    override fun getItemCount() = plants.size

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.plant_image)
        val plantName: TextView = itemView.findViewById(R.id.plant_name)
    }
}
