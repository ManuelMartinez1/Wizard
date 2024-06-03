package com.example.wizard.ui.home

import com.example.wizard.data.model.Bet
import com.example.wizard.data.model.Event
import com.example.wizard.data.model.Sport

interface HomeContract {
    interface View {
        fun showSportsAndEventCounts(sports: List<Sport>)
        fun showBets(bets: List<Bet>)
        fun showError(message: String)
    }

    interface Presenter {
        fun onViewCreated()
        fun onDestroyView()
    }
}
