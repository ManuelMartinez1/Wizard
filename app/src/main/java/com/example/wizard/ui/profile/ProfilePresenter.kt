package com.example.wizard.ui.profile

class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter {

    override fun onBetsSelected() {
        view.showBetsFragment()
    }

    override fun onStatsSelected() {
        view.showStatsFragment()
    }
}
