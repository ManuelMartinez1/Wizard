package com.example.wizard.ui.home

import DataManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wizard.R
import com.example.wizard.data.model.Sport

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var sports: List<Sport> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.games_item, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val sport = sports[position]
        holder.bind(sport)
    }

    override fun getItemCount(): Int {
        return sports.size
    }

    fun submitList(newList: List<Sport>) {
        sports = newList
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sport: Sport) {
            itemView.findViewById<TextView>(R.id.sport).text = sport.title
            itemView.findViewById<TextView>(R.id.games).text = "${sport.eventCount} games"
        }
    }
}





