package com.dicoding.dicodingevent.ui.favoriteEvent

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.database.FavoriteEvents
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Gunakan RecyclerView.Adapter biasa
class FavoriteEventAdapter(
    private var events: List<FavoriteEvents>,  // Daftar event menggunakan Parcelable
    private val onItemClick: (Int) -> Unit     // Callback ketika item diklik
) : RecyclerView.Adapter<FavoriteEventAdapter.EventViewHolderFav>() {

    class EventViewHolderFav(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView = view.findViewById(R.id.event_name)
        val eventImage: ImageView = view.findViewById(R.id.event_image)
        val eventDate: TextView = view.findViewById(R.id.event_date)
        val eventLocation: TextView = view.findViewById(R.id.event_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolderFav {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_available, parent, false)
        return EventViewHolderFav(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EventViewHolderFav, position: Int) {
        val event = events[position]

        holder.eventName.text = event.name
        holder.eventDate.text = "Date: ${formatDate(event.beginTime)}"
        holder.eventLocation.text = "Location: ${event.cityName}"

        // Load image with Glide
        Glide.with(holder.itemView.context)
            .load(event.image)
            .into(holder.eventImage)

        // Set on click item
        holder.itemView.setOnClickListener {
            onItemClick(event.id)
        }
    }

    override fun getItemCount(): Int = events.size

    // Tambahkan metode untuk memperbarui data di adapter
    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteEvents(newEvents: List<FavoriteEvents>) {
        events = newEvents
        notifyDataSetChanged()  // Beri tahu adapter bahwa data telah berubah
    }

    // Format date
    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy 'Pukul' HH.mm", Locale("id", "ID"))

        return try {
            val date: Date = inputFormat.parse(dateString) ?: Date()
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }
}
