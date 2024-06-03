package com.example.wizard.ui.bet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wizard.R
import com.example.wizard.data.model.Bet

class BetsAdapter : RecyclerView.Adapter<BetsAdapter.BetViewHolder>() {

    private var bets: List<Bet> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_straight, parent, false)
        return BetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
        val bet = bets[position]
        holder.bind(bet)
    }

    override fun getItemCount(): Int = bets.size

    fun submitList(newList: List<Bet>) {
        bets = newList
        notifyDataSetChanged()
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
}