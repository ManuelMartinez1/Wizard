package com.example.wizard.ui.home

import DataManager
import android.util.Log
import com.example.wizard.data.model.Bet
import com.example.wizard.data.model.Sport
import kotlinx.coroutines.*

class HomePresenter(private val view: HomeContract.View, private val dataManager: DataManager) : HomeContract.Presenter {
    private val presenterScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onViewCreated() {
        loadSports()
        loadBets()
    }

    private fun loadSports() {
        dataManager.obtenerDeportes { sportsList, error ->
            if (error == null && sportsList != null) {
                val sportsWithEventCounts = mutableListOf<Sport>()
                var pendingCount = sportsList.size

                sportsList.forEachIndexed { index, sport ->
                    val delayMillis = index * 55L // Delay 55 milliseconds between each request
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

    private fun loadBets() {
        dataManager.obtenerApuestas { betsList, error ->
            if (error == null && betsList != null) {
                val betsWithUserNames = mutableListOf<Bet>()
                var pendingCount = betsList.size

                betsList.forEach { bet ->
                    presenterScope.launch {
                        dataManager.obtenerNombreUsuarioPorId(bet.userId) { userName, error ->
                            if (error == null && userName != null) {
                                val betWithUserName = bet.copy(userName = userName)
                                betsWithUserNames.add(betWithUserName)
                                pendingCount--
                                if (pendingCount == 0) {
                                    view.showBets(betsWithUserNames)
                                }
                            } else {
                                view.showError(error?.message ?: "Error fetching user name")
                            }
                        }
                    }
                }
            } else {
                view.showError(error?.message ?: "Error fetching bets")
            }
        }
    }

    override fun onDestroyView() {
        presenterScope.cancel()
    }
}
