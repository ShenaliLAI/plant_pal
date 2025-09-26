package com.example.plantpal.growth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantpal.R

data class TimelineEntry(val date: String, val description: String, val height: String, val imageUri: String? = null)

class TimelineAdapter(private val entries: List<TimelineEntry>) : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_entry, parent, false)
        return TimelineViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val entry = entries[position]
        holder.date.text = entry.date
        holder.description.text = entry.description
        holder.height.text = entry.height

        if (entry.imageUri != null) {
            holder.image.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(entry.imageUri.toUri())
                .into(holder.image)
        } else {
            holder.image.visibility = View.GONE
        }
    }

    override fun getItemCount() = entries.size

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.timeline_date)
        val description: TextView = itemView.findViewById(R.id.timeline_description)
        val height: TextView = itemView.findViewById(R.id.timeline_height)
        val image: ImageView = itemView.findViewById(R.id.entry_image)
    }
}
