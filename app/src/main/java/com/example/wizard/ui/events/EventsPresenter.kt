package com.example.wizard.ui.events
import DataManager
import com.example.wizard.ui.events.EventsFragment

class EventsPresenter(private val dataManager: DataManager, private val fragment: EventsFragment) {

    fun loadEvents(sportKey: String) {
        fragment.showLoading()
        dataManager.obtenerEventosDesdeAPI(sportKey) { events, error ->
            fragment.hideLoading()
            if (error == null) {
                if (events != null) {
                    fragment.showEvents(events)
                }
            } else {
                fragment.showError(error.message ?: "Unknown error")
            }
        }
    }
}