package com.example.wizard.ui.home

import DataManager
import com.example.wizard.data.model.Sport

class HomePresenter(private val view: HomeContract.View, private val dataManager: DataManager) : HomeContract.Presenter {

    private var pendingCount = 0
    private val eventCountsMap = mutableMapOf<String, Int>()

    override fun onViewCreated() {
        obtenerDeportes()
    }

    override fun onDestroyView() {
        // Release resources or cancel operations if necessary
    }

    fun obtenerDeportes() {
        dataManager.obtenerDeportes { sportsList, error ->
            if (error == null && sportsList != null) {
                pendingCount = sportsList.size
                sportsList.forEach { sport ->
                    obtenerCantidadEventosPorDeporte(sport)
                }
            } else {
                view.showError(error?.message ?: "Unknown error")
            }
        }
    }

    private fun obtenerCantidadEventosPorDeporte(sport: Sport) {
        dataManager.obtenerCantidadEventosPorDeporte(sport.key) { eventCount, error ->
            if (error == null && eventCount != null) {
                synchronized(eventCountsMap) {
                    eventCountsMap[sport.title] = eventCount
                    checkIfAllDataLoaded()
                }
            } else {
                view.showError(error?.message ?: "Unknown error")
            }
        }
    }

    private fun checkIfAllDataLoaded() {
        if (eventCountsMap.size == pendingCount) {
            val sportsTitles = eventCountsMap.keys.toList()
            view.showSportsAndEventCounts(sportsTitles, eventCountsMap)
        }
    }
}