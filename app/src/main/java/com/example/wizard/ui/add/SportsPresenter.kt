package com.example.wizard.ui.add

import DataManager
import android.os.Build
import androidx.annotation.RequiresApi

class SportsPresenter(private val dataManager: DataManager, private val fragment: AddFragment) {

    @RequiresApi(Build.VERSION_CODES.O)
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