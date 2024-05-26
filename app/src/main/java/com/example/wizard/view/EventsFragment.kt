package com.example.wizard.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.wizard.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wizard.data.model.Event
import com.example.wizard.presenter.oddsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class EventsFragment : Fragment() {

    private lateinit var sportTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.event_spor_fragment, container, false)

        // Configurar TextView
        sportTextView = view.findViewById(R.id.sport)
        val sportTitle = arguments?.getString("title") ?: ""
        sportTextView.text = sportTitle
        sportTextView.textSize = 30f
        sportTextView.setTextColor(Color.WHITE)
        sportTextView.typeface = resources.getFont(R.font.inter_bold)

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        eventAdapter = EventAdapter(emptyList())
        recyclerView.adapter = eventAdapter

        //sportKey
        val sportKey = arguments?.getString("sportKey")
        if (!sportKey.isNullOrEmpty()) {
            obtenerEventosDesdeAPI(sportKey)
        }

        return view
    }


    private fun obtenerEventosDesdeAPI(sportKey: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.the-odds-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(oddsApiService::class.java)
        val apiKey = "93431bcf5f7d7994a9a97f41af907539"
        val call = service.getEventsForSport(sportKey, apiKey)

        call.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val events = response.body() ?: emptyList()
                    updateEvents(events)
                } else {

                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {

            }
        })
    }

    // actualizar los eventos en el RecyclerView
    private fun updateEvents(events: List<Event>) {
        eventAdapter = EventAdapter(events)
        recyclerView.adapter = eventAdapter
    }
}

