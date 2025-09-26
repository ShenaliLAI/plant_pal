package com.example.plantpal.plants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.plantpal.R
import com.example.plantpal.MyGardenRepository
import android.widget.Toast

class PlantAdapter(private val plantList: List<Plant>) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plantList[position]
        holder.plantImage.setImageResource(plant.imageResId)
        holder.plantName.text = plant.name
        holder.scientificName.text = plant.scientificName
        holder.plantDescription.text = plant.description
        holder.wateringInfo.text = plant.watering
        holder.sunlightInfo.text = plant.sunlight
        holder.temperatureInfo.text = plant.temperature
    }

    override fun getItemCount() = plantList.size

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.plant_image)
        val plantName: TextView = itemView.findViewById(R.id.plant_name)
        val scientificName: TextView = itemView.findViewById(R.id.scientific_name)
        val plantDescription: TextView = itemView.findViewById(R.id.plant_description)
        val wateringInfo: TextView = itemView.findViewById(R.id.watering_info)
        val sunlightInfo: TextView = itemView.findViewById(R.id.sunlight_info)
        val temperatureInfo: TextView = itemView.findViewById(R.id.temperature_info)
        val detailsArrow: ImageView = itemView.findViewById(R.id.details_arrow)
        val addButton: ImageView = itemView.findViewById(R.id.add_button)

        init {
            addButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val plant = plantList[position]
                    MyGardenRepository.addPlant(plant)
                    Toast.makeText(itemView.context, "${plant.name} added to My Garden", Toast.LENGTH_SHORT).show()
                }
            }

            detailsArrow.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val plant = plantList[position]
                    val intent = Intent(itemView.context, PlantDetailsActivity::class.java).apply {
                        putExtra("PLANT_EXTRA", plant)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
