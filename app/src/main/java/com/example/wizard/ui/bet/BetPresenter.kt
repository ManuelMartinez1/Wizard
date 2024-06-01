package com.example.wizard.ui.bet

import DataManager

// BetPresenter.kt
class BetPresenter(private val dataManager: DataManager, private val view: BetFragment) : BetContract.Presenter {

    override fun loadEventOdds(sportKey: String, eventId: String) {
        view.showLoading()
        dataManager.obtenerEventOdds(sportKey, eventId) { eventOdds, error ->
            view.hideLoading()
            if (error == null) {
                if (eventOdds != null) {
                    view.showEventOdds(eventOdds)
                }
            } else {
                view.showError(error.message ?: "Unknown error")
            }
        }
    }
}
