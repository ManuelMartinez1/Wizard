package com.example.wizard.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.wizard.R
import com.example.wizard.data.model.Ticket

class TicketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ticket, container, false)

        // Get ticket data from arguments
        val winnerTeamOne = arguments?.getString("winnerTeamOne")
        val winnerTeamTwo = arguments?.getString("winnerTeamTwo")
        val winnerMarket = arguments?.getString("winnerMarket")
        val winnerBet = arguments?.getString("winnerBet")
        val winnerOdd = arguments?.getString("winnerOdd")

        val handicapTeamOne = arguments?.getString("handicapTeamOne")
        val handicapTeamTwo = arguments?.getString("handicapTeamTwo")
        val handicapMarket = arguments?.getString("handicapMarket")
        val handicapBet = arguments?.getString("handicapBet")
        val handicapOdd = arguments?.getString("handicapOdd")

        val totalsTeamOne = arguments?.getString("totalsTeamOne")
        val totalsTeamTwo = arguments?.getString("totalsTeamTwo")
        val totalsMarket = arguments?.getString("totalsMarket")
        val totalsBet = arguments?.getString("totalsBet")
        val totalsOdd = arguments?.getString("totalsOdd")

        // Inflate tickets layout and add to container
        val ticketContainer = view.findViewById<LinearLayout>(R.id.ticketContainer)
        val inflater = LayoutInflater.from(context)

        winnerTeamOne?.let {
            val ticketView = inflater.inflate(R.layout.tickets, ticketContainer, false)
            ticketView.findViewById<TextView>(R.id.teamone).text = winnerTeamOne
            ticketView.findViewById<TextView>(R.id.teamtwo).text = winnerTeamTwo
            ticketView.findViewById<TextView>(R.id.market).text = winnerMarket
            ticketView.findViewById<TextView>(R.id.Bet).text = winnerBet
            ticketView.findViewById<TextView>(R.id.odd).text = winnerOdd
            ticketContainer.addView(ticketView)
        }

        handicapTeamOne?.let {
            val ticketView = inflater.inflate(R.layout.tickets, ticketContainer, false)
            ticketView.findViewById<TextView>(R.id.teamone).text = handicapTeamOne
            ticketView.findViewById<TextView>(R.id.teamtwo).text = handicapTeamTwo
            ticketView.findViewById<TextView>(R.id.market).text = handicapMarket
            ticketView.findViewById<TextView>(R.id.Bet).text = handicapBet
            ticketView.findViewById<TextView>(R.id.odd).text = handicapOdd
            ticketContainer.addView(ticketView)
        }

        totalsTeamOne?.let {
            val ticketView = inflater.inflate(R.layout.tickets, ticketContainer, false)
            ticketView.findViewById<TextView>(R.id.teamone).text = totalsTeamOne
            ticketView.findViewById<TextView>(R.id.teamtwo).text = totalsTeamTwo
            ticketView.findViewById<TextView>(R.id.market).text = totalsMarket
            ticketView.findViewById<TextView>(R.id.Bet).text = totalsBet
            ticketView.findViewById<TextView>(R.id.odd).text = totalsOdd
            ticketContainer.addView(ticketView)
        }

        return view
    }
}