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
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wizard.data.model.Bet
import com.example.wizard.data.model.Sport
import com.example.wizard.ui.bet.BetsAdapter

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private lateinit var adapter: HomeAdapter
    private lateinit var betsAdapter: BetsAdapter  // Adapter for displaying bets

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenter(this, DataManager())

        adapter = HomeAdapter()
        betsAdapter = BetsAdapter()  // Initialize the bets adapter

        val sportsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        sportsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        sportsRecyclerView.adapter = adapter

        val betsRecyclerView = view.findViewById<RecyclerView>(R.id.betsRecyclerView)  // Assuming you have another RecyclerView for bets
        betsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        betsRecyclerView.adapter = betsAdapter

        presenter.onViewCreated()
    }

    override fun showSportsAndEventCounts(sports: List<Sport>) {
        adapter.submitSportList(sports)
    }

    override fun showBets(bets: List<Bet>) {
        betsAdapter.submitList(bets)  // Update the bets adapter
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }
}