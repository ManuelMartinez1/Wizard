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
import com.example.wizard.data.model.Bet
import com.example.wizard.data.model.Sport

class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var sports: List<Sport> = emptyList()
    private var bets: List<Bet> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return if (position < sports.size) {
            VIEW_TYPE_SPORT
        } else {
            VIEW_TYPE_BET
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SPORT -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.games_item, parent, false)
                SportViewHolder(itemView)
            }
            VIEW_TYPE_BET -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_straight, parent, false)
                BetViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SportViewHolder) {
            val sport = sports[position]
            holder.bind(sport)
        } else if (holder is BetViewHolder) {
            val bet = bets[position - sports.size]
            holder.bind(bet)
        }
    }

    override fun getItemCount(): Int {
        return sports.size + bets.size
    }

    fun submitSportList(newList: List<Sport>) {
        sports = newList
        notifyDataSetChanged()
    }

    fun submitBetList(newList: List<Bet>) {
        bets = newList
        notifyDataSetChanged()
    }

    inner class SportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sport: Sport) {
            itemView.findViewById<TextView>(R.id.sport).text = sport.title
            itemView.findViewById<TextView>(R.id.games).text = "${sport.eventCount} games"
        }
    }

    inner class BetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bet: Bet) {
            itemView.findViewById<TextView>(R.id.Name).text = bet.userName
            itemView.findViewById<TextView>(R.id.market).text = bet.market
            itemView.findViewById<TextView>(R.id.apuesta).text = bet.bet
            itemView.findViewById<TextView>(R.id.partido).text = "${bet.teamOne} - ${bet.teamTwo}"
            itemView.findViewById<TextView>(R.id.odd).text = bet.odd.toString()
        }
    }

    companion object {
        private const val VIEW_TYPE_SPORT = 1
        private const val VIEW_TYPE_BET = 2
    }
}
