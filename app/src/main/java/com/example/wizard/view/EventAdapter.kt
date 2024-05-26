package com.example.wizard.view
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wizard.R
import com.example.wizard.data.model.Event
import java.text.SimpleDateFormat
import java.util.Locale

class EventAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item_custom, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventTextView: TextView = itemView.findViewById(R.id.eventTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)

        fun bind(event: Event) {
            val eventText = "${event.home_team} - ${event.away_team}"
            eventTextView.text = eventText


            val formattedTime = formatTime(event.commence_time)
            timeTextView.text = formattedTime
        }

        private fun formatTime(commenceTime: String): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = formatter.parse(commenceTime)
            val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return timeFormatter.format(date)
        }
    }
}
