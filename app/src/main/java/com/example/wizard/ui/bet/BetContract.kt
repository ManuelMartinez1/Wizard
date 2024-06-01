package com.example.wizard.ui.bet

import com.example.wizard.data.model.EventOdds

// BetContract.kt
interface BetContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showEventOdds(eventOdds: EventOdds)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadEventOdds(sportKey: String, eventId: String)
    }
}
