package com.example.wizard.ui.home

import com.example.wizard.data.model.Event
import com.example.wizard.data.model.Sport

interface HomeContract {
    interface View {
        fun showError(message: String)
        fun showSportsAndEventCounts(toList: List<String>, eventCountsMap: MutableMap<String, Int>)
    }

    interface Presenter {
        fun onViewCreated()
        fun onDestroyView()
        }
}