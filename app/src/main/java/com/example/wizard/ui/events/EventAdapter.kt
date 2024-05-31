package com.example.wizard.ui.events
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wizard.R
import com.example.wizard.data.model.Event
import com.example.wizard.ui.bet.BetFragment
import java.text.SimpleDateFormat
import java.util.Locale

class EventAdapter(private val events: List<Event>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item_custom, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        holder.itemView.setOnClickListener {
            navigateToBetFragment(fragmentManager, event)
        }
    }

    override fun getItemCount(): Int = events.size

    private fun navigateToBetFragment(fragmentManager: FragmentManager, event: Event) {
        val fragment = BetFragment().apply {
            arguments = Bundle().apply {
                putString("sportKey", event.sport_key)
                putString("eventId", event.id)
                putString("eventTitle", "${event.home_team} vs ${event.away_team}")
            }
        }
        fragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val homeTeamTextView: TextView = itemView.findViewById(R.id.eventTextView)
        private val awayTeamTextView: TextView = itemView.findViewById(R.id.eventTextView2)
        private val homeTeamMomioTextView: TextView = itemView.findViewById(R.id.momio)
        private val awayTeamMomioTextView: TextView = itemView.findViewById(R.id.momio2)
        private val homeTeamNameTextView: TextView = itemView.findViewById(R.id.equipo)
        private val awayTeamNameTextView: TextView = itemView.findViewById(R.id.equipo2)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)

        fun bind(event: Event) {
            homeTeamTextView.text = event.home_team
            awayTeamTextView.text = event.away_team
            homeTeamMomioTextView.text = "000" // Actualizar con el valor correspondiente si está disponible
            awayTeamMomioTextView.text = "000" // Actualizar con el valor correspondiente si está disponible
            homeTeamNameTextView.text = event.home_team
            awayTeamNameTextView.text = event.away_team

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
