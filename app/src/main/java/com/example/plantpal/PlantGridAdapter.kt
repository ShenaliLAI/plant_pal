package com.example.plantpal

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.plantpal.growth.GrowthTrackerActivity
import com.example.plantpal.plants.Plant
import com.example.plantpal.reminders.AddReminderActivity

class PlantGridAdapter(private val context: Context, private val plantList: List<Plant>, private val onDeleteClick: (Plant) -> Unit) : BaseAdapter() {

    override fun getCount(): Int = plantList.size

    override fun getItem(position: Int): Any = plantList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_plant, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val plant = getItem(position) as Plant
        viewHolder.plantName.text = plant.name
        viewHolder.plantImage.setImageResource(plant.imageResId)

        viewHolder.reminderIcon.setOnClickListener {
            val intent = Intent(context, AddReminderActivity::class.java)
            intent.putExtra("plant_name", plant.name)
            context.startActivity(intent)
        }

        viewHolder.growthTrackerIcon.setOnClickListener {
            val plantsWithGrowthData = listOf("Rose", "Sunflower", "Lavender", "Marigold", "Tulip", "Orchid")
            if (plantsWithGrowthData.contains(plant.name)) {
                val intent = Intent(context, GrowthTrackerActivity::class.java)
                intent.putExtra("PLANT_NAME", plant.name)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Growth has not been tracked yet", Toast.LENGTH_SHORT).show()
            }
        }

        viewHolder.deleteIcon.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Remove Plant")
                .setMessage("Are you sure you want to remove this plant from your garden?")
                .setPositiveButton("Yes") { _, _ ->
                    onDeleteClick(plant)
                }
                .setNegativeButton("No", null)
                .show()
        }

        return view
    }

    private class ViewHolder(view: View) {
        val plantImage: ImageView = view.findViewById(R.id.plant_image)
        val plantName: TextView = view.findViewById(R.id.plant_name)
        val reminderIcon: ImageView = view.findViewById(R.id.reminder_icon)
        val deleteIcon: ImageView = view.findViewById(R.id.delete_icon)
        val growthTrackerIcon: ImageView = view.findViewById(R.id.growth_tracker_icon)
    }
}
