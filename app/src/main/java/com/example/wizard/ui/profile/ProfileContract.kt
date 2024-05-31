package com.example.wizard.ui.profile

interface ProfileContract {

    interface View {
        fun showBetsFragment()
        fun showStatsFragment()
    }

    interface Presenter {
        fun onBetsSelected()
        fun onStatsSelected()
    }
}
