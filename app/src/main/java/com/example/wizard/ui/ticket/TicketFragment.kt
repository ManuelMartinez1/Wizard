package com.example.wizard.ui.ticket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.wizard.R
import com.example.wizard.data.model.Bet
import com.example.wizard.data.model.Ticket
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TicketFragment : Fragment() {

    private lateinit var postBetsButton: Button
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ticket, container, false)

        // Initialize Firebase
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Initialize post bets button
        postBetsButton = view.findViewById(R.id.postbets)
        postBetsButton.setOnClickListener {
            postBets()
        }

        // Get ticket data from arguments and display them
        displayTickets(view)

        return view
    }

    private fun displayTickets(view: View) {
        val ticketContainer = view.findViewById<LinearLayout>(R.id.ticketContainer)
        val inflater = LayoutInflater.from(context)

        arguments?.let {
            val winnerTeamOne = it.getString("winnerTeamOne")
            val winnerTeamTwo = it.getString("winnerTeamTwo")
            val winnerMarket = it.getString("winnerMarket")
            val winnerBet = it.getString("winnerBet")
            val winnerOdd = it.getString("winnerOdd")

            val handicapTeamOne = it.getString("handicapTeamOne")
            val handicapTeamTwo = it.getString("handicapTeamTwo")
            val handicapMarket = it.getString("handicapMarket")
            val handicapBet = it.getString("handicapBet")
            val handicapOdd = it.getString("handicapOdd")

            val totalsTeamOne = it.getString("totalsTeamOne")
            val totalsTeamTwo = it.getString("totalsTeamTwo")
            val totalsMarket = it.getString("totalsMarket")
            val totalsBet = it.getString("totalsBet")
            val totalsOdd = it.getString("totalsOdd")

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
        }
    }

    private fun postBets() {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(context, "No user is logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = user.uid
        val tickets = mutableListOf<Bet>()

        arguments?.let {
            val winnerTeamOne = it.getString("winnerTeamOne")
            val winnerTeamTwo = it.getString("winnerTeamTwo")
            val winnerMarket = it.getString("winnerMarket")
            val winnerBet = it.getString("winnerBet")
            val winnerOdd = it.getString("winnerOdd")?.toDouble()

            val handicapTeamOne = it.getString("handicapTeamOne")
            val handicapTeamTwo = it.getString("handicapTeamTwo")
            val handicapMarket = it.getString("handicapMarket")
            val handicapBet = it.getString("handicapBet")
            val handicapOdd = it.getString("handicapOdd")?.toDouble()

            val totalsTeamOne = it.getString("totalsTeamOne")
            val totalsTeamTwo = it.getString("totalsTeamTwo")
            val totalsMarket = it.getString("totalsMarket")
            val totalsBet = it.getString("totalsBet")
            val totalsOdd = it.getString("totalsOdd")?.toDouble()

            winnerTeamOne?.let {
                val bet = Bet(
                    teamOne = winnerTeamOne,
                    teamTwo = winnerTeamTwo ?: "",
                    market = winnerMarket ?: "",
                    bet = winnerBet ?: "",
                    odd = winnerOdd ?: 0.0,
                    userId = userId
                )
                tickets.add(bet)
            }

            handicapTeamOne?.let {
                val bet = Bet(
                    teamOne = handicapTeamOne,
                    teamTwo = handicapTeamTwo ?: "",
                    market = handicapMarket ?: "",
                    bet = handicapBet ?: "",
                    odd = handicapOdd ?: 0.0,
                    userId = userId
                )
                tickets.add(bet)
            }

            totalsTeamOne?.let {
                val bet = Bet(
                    teamOne = totalsTeamOne,
                    teamTwo = totalsTeamTwo ?: "",
                    market = totalsMarket ?: "",
                    bet = totalsBet ?: "",
                    odd = totalsOdd ?: 0.0,
                    userId = userId
                )
                tickets.add(bet)
            }
        }

        tickets.forEach { bet ->
            db.collection("bets")
                .add(bet)
                .addOnSuccessListener {
                    Toast.makeText(context, "Bet posted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e("TicketFragment", "Error posting bet", e)
                    Toast.makeText(context, "Failed to post bet", Toast.LENGTH_SHORT).show()
                }
        }
    }
}