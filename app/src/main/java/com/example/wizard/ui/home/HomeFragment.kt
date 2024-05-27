package com.example.wizard.ui.home

import DataManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.wizard.R
import com.example.wizard.data.model.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.example.wizard.data.model.Sport
import com.example.wizard.data.remote.api.oddsApiService

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private val dataManager: DataManager = DataManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomePresenter(this, dataManager)
        presenter.onViewCreated()
        (presenter as HomePresenter).obtenerDeportes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun showSportsAndEventCounts(
        sportsTitles: List<String>,
        eventCountsMap: MutableMap<String, Int>
    ) {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.linearLayout)

        sportsTitles.forEach { sportTitle ->
            // Inflate the item layout
            val circleLayout = layoutInflater.inflate(R.layout.games_item, linearLayout, false)

            // Get references to the views within the item layout
            val leagueTextView = circleLayout.findViewById<TextView>(R.id.sport)
            val gamesTextView = circleLayout.findViewById<TextView>(R.id.games)

            // Set the sport title and event count
            leagueTextView.text = sportTitle
            val eventCount = eventCountsMap[sportTitle] ?: 0
            gamesTextView.text = "$eventCount games"

            // Set custom typeface if needed (using ResourcesCompat for backward compatibility)
            val typeface = ResourcesCompat.getFont(requireContext(), R.font.inter_bold)
            leagueTextView.typeface = typeface
            gamesTextView.typeface = typeface

            // Add the item layout to the LinearLayout
            linearLayout?.addView(circleLayout)
        }
    }

    override fun showError(message: String) {
        // Handle errors
    }
}