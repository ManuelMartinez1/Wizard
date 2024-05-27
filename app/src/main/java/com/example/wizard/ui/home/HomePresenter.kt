package com.example.wizard.ui.home

import DataManager
import com.example.wizard.data.model.Sport
import kotlinx.coroutines.*

class HomePresenter(private val view: HomeContract.View, private val dataManager: DataManager) : HomeContract.Presenter {
    private val presenterScope = CoroutineScope(Dispatchers.Main + Job())
    override fun onViewCreated() {
        dataManager.obtenerDeportes { sportsList, error ->
            if (error == null && sportsList != null) {
                val sportsWithEventCounts = mutableListOf<Sport>()
                var pendingCount = sportsList.size

                sportsList.forEachIndexed { index, sport ->
                    // Introduce a delay between requests to prevent rate limiting
                    val delayMillis = index * 55L // Delay 65 milliseconds between each request
                    presenterScope.launch {
                        delay(delayMillis)
                        dataManager.obtenerCantidadEventosPorDeporte(sport.key) { eventCount, error ->
                            if (error == null && eventCount != null) {
                                sportsWithEventCounts.add(Sport(sport.title, sport.key, eventCount))
                                pendingCount--
                                if (pendingCount == 0) {
                                    view.showSportsAndEventCounts(sportsWithEventCounts)
                                }
                            } else {
                                view.showError(error?.message ?: "Unknown error")
                            }
                        }
                    }
                }
            } else {
                view.showError(error?.message ?: "Unknown error")
            }
        }
    }

    override fun onDestroyView() {
        // Lógica para cuando la vista está siendo destruida
    }
}


