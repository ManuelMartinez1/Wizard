package com.example.wizard.ui.bet

import DataManager
import com.example.wizard.data.remote.api.OddsApiService

class BetPresenter(private val dataManager: DataManager, private val fragment: BetFragment) {

    fun loadEventOdds(sportKey: String, eventId: String){
        fragment.showLoading()
        dataManager.obtenerEventOdds(sportKey, eventId){ eventOdds, error ->
            fragment.hideLoading()
            if(error == null){
                if(eventOdds != null){
                    fragment.showEventOdds(eventOdds)
                }
            }else{
                fragment.showError(error.message ?: "Unknown error")
            }
        }
    }
}