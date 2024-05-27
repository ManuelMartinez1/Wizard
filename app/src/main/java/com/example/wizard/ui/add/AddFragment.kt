package com.example.wizard.ui.add

import DataManager
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.wizard.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.wizard.data.model.Sport
import com.example.wizard.data.remote.api.oddsApiService
import com.example.wizard.ui.events.EventsFragment


class AddFragment : Fragment() {

    private lateinit var presenter: SportsPresenter
    private val dataManager = DataManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SportsPresenter(dataManager, this)
        presenter.loadSports()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showSports(sportsList: List<Sport>) {
        val sportsLayout = view?.findViewById<LinearLayout>(R.id.sports)

        // Deportes que no contienen "winner" en su título
        val filteredSports = sportsList.filter { sport ->
            !sport.title.contains("winner", ignoreCase = true)
        }

        for (sport in filteredSports) {
            val sportButton = Button(context, null, 0, R.style.SportButtonStyle)
            sportButton.text = sport.title
            sportButton.textSize = 20f
            sportButton.setTextColor(Color.WHITE)
            sportButton.typeface = resources.getFont(R.font.inter_bold)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 4, 0, 16)
            sportButton.layoutParams = params

            sportButton.setOnClickListener {
                val activity = requireActivity()
                if (activity is AppCompatActivity) {
                    val fragmentManager = activity.supportFragmentManager
                    navigateToEventsFragment(fragmentManager, sport.key, sport.title)
                    Log.d("SPORT_CLICK", "Click en: ${sport.title} (${sport.key}).")
                }
            }

            sportsLayout?.addView(sportButton)
        }
    }

    fun showError(message: String) {
        // Mostrar un mensaje de error
    }

    private fun navigateToEventsFragment(fragmentManager: FragmentManager, sportKey: String, sportTitle: String) {
        val fragment = EventsFragment()
        val bundle = Bundle()
        bundle.putString("sportKey", sportKey)
        bundle.putString("title", sportTitle)
        fragment.arguments = bundle

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
        }
}
