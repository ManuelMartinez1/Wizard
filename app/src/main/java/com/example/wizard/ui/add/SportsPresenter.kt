package com.example.wizard.ui.add

import DataManager

class SportsPresenter(private val dataManager: DataManager, private val fragment: AddFragment) {

    fun loadSports() {
        dataManager.obtenerDeportes { sportsList, error ->
            if (error == null && sportsList != null) {
                fragment.showSports(sportsList)
            } else {
                fragment.showError(error?.message ?: "Unknown error")
            }
        }
    }
}