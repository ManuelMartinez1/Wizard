package com.example.wizard.ui.events

import DataManager
import androidx.fragment.app.Fragment
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.wizard.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wizard.data.model.Event
import com.example.wizard.ui.bet.BetFragment

class EventsFragment : Fragment() {

    private lateinit var sportTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var presenter: EventsPresenter

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

        // Inicializar el Presenter
        presenter = EventsPresenter(DataManager(), this)

        // sportKey
        val sportKey = arguments?.getString("sportKey")
        if (!sportKey.isNullOrEmpty()) {
            presenter.loadEvents(sportKey)
        }

        return view
    }

    fun showLoading() {
        // Mostrar una indicación de carga
    }

    fun hideLoading() {
        // Ocultar la indicación de carga
    }

    fun showEvents(events: List<Event>) {
        eventAdapter = EventAdapter(events, parentFragmentManager)
        recyclerView.adapter = eventAdapter
    }

    fun showError(message: String) {
        // Mostrar un mensaje de error
    }

    private fun navigateToBetFragment(fragmentManager: FragmentManager, eventId: String, eventTitle: String) {
        val fragment = BetFragment()
        val bundle = Bundle().apply {
            putString("eventId", eventId)
            putString("eventTitle", eventTitle)
        }
        fragment.arguments = bundle

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}

