package com.example.wizard.ui.bet

import DataManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.wizard.R
import com.example.wizard.data.model.EventOdds
import com.example.wizard.ui.ticket.TicketFragment

class BetFragment : Fragment() {

    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var winnerLayout: View
    private lateinit var handicapLayout: View
    private lateinit var totalsLayout: View
    private lateinit var circularButton: Button
    private lateinit var titleTeams: TextView
    private lateinit var presenter: BetPresenter
    private var eventId: String = ""
    private var sportKey: String = ""

    private var selectedWinnerButton: View? = null
    private var selectedHandicapButton: View? = null
    private var selectedTotalsButton: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bet, container, false)

        // Initialize views
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        winnerLayout = view.findViewById(R.id.winnerLayout)
        handicapLayout = view.findViewById(R.id.handicapLayout)
        totalsLayout = view.findViewById(R.id.totalsLayout)
        circularButton = view.findViewById(R.id.circularButton)
        titleTeams = view.findViewById(R.id.vs)

        // Initialize presenter
        presenter = BetPresenter(DataManager(), this)

        // Get sportKey and eventId from arguments
        arguments?.let {
            sportKey = it.getString("sportKey", "")
            eventId = it.getString("eventId", "")
            val eventTitle = it.getString("eventTitle", "")
            // Set event title if needed
            val teams = eventTitle.split(" vs ")
            val formattedTeam1 = teams[0].take(3).toUpperCase()
            val formattedTeam2 = teams[1].take(3).toUpperCase()

            // Set event title with formatted team names
            titleTeams.text = "$formattedTeam1 - $formattedTeam2"
        }

        // Load event odds
        presenter.loadEventOdds(sportKey, eventId)

        // Set circular button click listener
        circularButton.setOnClickListener {
            navigateToTicketFragment()
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = BetPresenter(DataManager(), this)
        arguments?.let {
            sportKey = it.getString("sportKey", "")
            eventId = it.getString("eventId", "")
            val eventTitle = it.getString("eventTitle", "")
            // Set event title if needed
        }
        presenter.loadEventOdds(sportKey, eventId)
        circularButton.setOnClickListener {
            navigateToTicketFragment()
        }
    }

    private fun navigateToTicketFragment() {
        val fragment = TicketFragment()
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun showLoading() {
        loadingProgressBar.visibility = View.VISIBLE
        winnerLayout.visibility = View.GONE
        handicapLayout.visibility = View.GONE
        totalsLayout.visibility = View.GONE
    }

    fun hideLoading() {
        loadingProgressBar.visibility = View.GONE
    }

    private fun updateCircularButtonVisibility() {
        circularButton.visibility = if (selectedWinnerButton != null || selectedHandicapButton != null || selectedTotalsButton != null) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setButtonClickListener(button: View, layoutType: String) {
        button.setOnClickListener {
            when (layoutType) {
                "winner" -> {
                    if (selectedWinnerButton == button) {
                        button.isSelected = false
                        selectedWinnerButton = null
                    } else {
                        selectedWinnerButton?.isSelected = false
                        button.isSelected = true
                        selectedWinnerButton = button
                    }
                }
                "handicap" -> {
                    if (selectedHandicapButton == button) {
                        button.isSelected = false
                        selectedHandicapButton = null
                    } else {
                        selectedHandicapButton?.isSelected = false
                        button.isSelected = true
                        selectedHandicapButton = button
                    }
                }
                "totals" -> {
                    if (selectedTotalsButton == button) {
                        button.isSelected = false
                        selectedTotalsButton = null
                    } else {
                        selectedTotalsButton?.isSelected = false
                        button.isSelected = true
                        selectedTotalsButton = button
                    }
                }
            }
            updateCircularButtonVisibility()
        }
    }

    fun showEventOdds(eventOdds: EventOdds) {
        Log.d("BetFragment", "EventOdds response: $eventOdds")

        val draftKings = eventOdds.bookmakers.firstOrNull { it.key == "draftkings" }
        Log.d("BetFragment", "DraftKings: $draftKings")

        val winnerMarket = draftKings?.markets?.firstOrNull { it.key == "h2h" }
        val handicapMarket = draftKings?.markets?.firstOrNull { it.key == "spreads" }
        val totalsMarket = draftKings?.markets?.firstOrNull { it.key == "totals" }

        Log.d("BetFragment", "Winner Market: $winnerMarket")
        Log.d("BetFragment", "Handicap Market: $handicapMarket")
        Log.d("BetFragment", "Totals Market: $totalsMarket")

        winnerMarket?.let {
            val outcomes = it.outcomes
            if (outcomes.size >= 2) {
                val customButton1 = winnerLayout.findViewById<View>(R.id.customButton1)
                val customButton2 = winnerLayout.findViewById<View>(R.id.customButton2)

                val leftText1: TextView = customButton1.findViewById(R.id.leftText)
                val rightText1: TextView = customButton1.findViewById(R.id.rightText)
                val leftText2: TextView = customButton2.findViewById(R.id.leftText)
                val rightText2: TextView = customButton2.findViewById(R.id.rightText)

                leftText1.text = outcomes[0].name
                rightText1.text = outcomes[0].price.toString()
                leftText2.text = outcomes[1].name
                rightText2.text = outcomes[1].price.toString()

                setButtonClickListener(customButton1, "winner")
                setButtonClickListener(customButton2, "winner")
            }
            winnerLayout.visibility = View.VISIBLE
        } ?: run {
            winnerLayout.visibility = View.GONE
        }

        handicapMarket?.let {
            val outcomes = it.outcomes
            if (outcomes.size >= 2) {
                val titleTextView: TextView = handicapLayout.findViewById(R.id.eventTextView)
                titleTextView.text = "Handicap"

                val team1TextView: TextView = handicapLayout.findViewById(R.id.team1)
                val team2TextView: TextView = handicapLayout.findViewById(R.id.team2)
                val customButton1 = handicapLayout.findViewById<View>(R.id.betbtn1)
                val customButton2 = handicapLayout.findViewById<View>(R.id.betbtn2)

                val leftText1: TextView = customButton1.findViewById(R.id.leftText)
                val rightText1: TextView = customButton1.findViewById(R.id.rightText)
                val leftText2: TextView = customButton2.findViewById(R.id.leftText)
                val rightText2: TextView = customButton2.findViewById(R.id.rightText)

                team1TextView.text = outcomes[0].name
                team2TextView.text = outcomes[1].name

                leftText1.text = outcomes[0].point.toString()
                rightText1.text = outcomes[0].price.toString()
                leftText2.text = outcomes[1].point.toString()
                rightText2.text = outcomes[1].price.toString()

                setButtonClickListener(customButton1, "handicap")
                setButtonClickListener(customButton2, "handicap")
            }
            handicapLayout.visibility = View.VISIBLE
        } ?: run {
            handicapLayout.visibility = View.GONE
        }



        totalsMarket?.let {
            val outcomes = it.outcomes
            if (outcomes.size >= 2) {
                val titleTextView: TextView = totalsLayout.findViewById(R.id.eventTextView)
                titleTextView.text = "Totals"

                val team1TextView: TextView = totalsLayout.findViewById(R.id.team1)
                val team2TextView: TextView = totalsLayout.findViewById(R.id.team2)
                val customButton1 = totalsLayout.findViewById<View>(R.id.betbtn1)
                val customButton2 = totalsLayout.findViewById<View>(R.id.betbtn2)

                val leftText1: TextView = customButton1.findViewById(R.id.leftText)
                val rightText1: TextView = customButton1.findViewById(R.id.rightText)
                val leftText2: TextView = customButton2.findViewById(R.id.leftText)
                val rightText2: TextView = customButton2.findViewById(R.id.rightText)

                team1TextView.text = outcomes[0].name
                team2TextView.text = outcomes[1].name

                leftText1.text = outcomes[0].point.toString()
                rightText1.text = outcomes[0].price.toString()
                leftText2.text = outcomes[1].point.toString()
                rightText2.text = outcomes[1].price.toString()

                setButtonClickListener(customButton1, "totals")
                setButtonClickListener(customButton2, "totals")
            }
            totalsLayout.visibility = View.VISIBLE
        } ?: run {
            totalsLayout.visibility = View.GONE
        }
    }

    fun showError(message: String) {
        Log.e("BetFragment", "Error: $message")
    }
}