package com.example.wizard.ui.ticket

import android.os.Bundle
import com.example.wizard.data.model.Ticket

class TicketPresenter(private val view: TicketView) {

    interface TicketView {
        fun showTickets(tickets: List<Ticket>)
        fun showError(message: String)
    }

    fun loadTickets(arguments: Bundle?) {
        val tickets = mutableListOf<Ticket>()

        arguments?.let {
            // Winner Ticket
            val winnerTeamOne = it.getString("winnerTeamOne")
            val winnerTeamTwo = it.getString("winnerTeamTwo")
            val winnerMarket = it.getString("winnerMarket")
            val winnerBet = it.getString("winnerBet")
            val winnerOdd = it.getString("winnerOdd")

            if (winnerTeamOne != null && winnerTeamTwo != null && winnerMarket != null && winnerBet != null && winnerOdd != null) {
                val winnerTicket = Ticket(winnerTeamOne, winnerTeamTwo, winnerMarket, winnerBet, winnerOdd)
                tickets.add(winnerTicket)
            }

            // Handicap Ticket
            val handicapTeamOne = it.getString("handicapTeamOne")
            val handicapTeamTwo = it.getString("handicapTeamTwo")
            val handicapMarket = it.getString("handicapMarket")
            val handicapBet = it.getString("handicapBet")
            val handicapOdd = it.getString("handicapOdd")

            if (handicapTeamOne != null && handicapTeamTwo != null && handicapMarket != null && handicapBet != null && handicapOdd != null) {
                val handicapTicket = Ticket(handicapTeamOne, handicapTeamTwo, handicapMarket, handicapBet, handicapOdd)
                tickets.add(handicapTicket)
            }

            if (tickets.isNotEmpty()) {
                view.showTickets(tickets)
            } else {
                view.showError("No tickets available")
            }
        } ?: run {
            view.showError("No arguments provided")
        }
    }
}
