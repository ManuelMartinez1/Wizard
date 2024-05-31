package com.example.wizard.ui.home

import DataManager
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
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
        Log.d("HomeAdapter", "Binding sport at position $position: ${sport.title}, ${sport.eventCount} events")
        holder.bind(sport)
    }

    override fun getItemCount(): Int {
        val itemCount = sports.size
        Log.d("HomeAdapter", "Total items count: $itemCount")
        return itemCount
    }

    fun submitList(newList: List<Sport>) {
        Log.d("HomeAdapter", "New list submitted: ${newList.size} items")
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


