package com.example.wizard.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.wizard.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.wizard.presenter.oddsApiService
import android.graphics.Typeface
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.wizard.data.model.Sport


class AddFragment : Fragment() {

    private lateinit var apiService: oddsApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.the-odds-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(oddsApiService::class.java)
        getSports()
    }

    private fun getSports() {
        val call: Call<List<Sport>> = apiService.getSports("93431bcf5f7d7994a9a97f41af907539")
        call.enqueue(object : Callback<List<Sport>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<List<Sport>>, response: Response<List<Sport>>) {
                if (response.isSuccessful) {
                    val sportsList = response.body()
                    sportsList?.let {
                        val sportsLayout = view?.findViewById<LinearLayout>(R.id.sports)

                        // deportes que no contienen "winner" en su title
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
                } else {
                    // Manejar errores de la respuesta
                }
            }

            override fun onFailure(call: Call<List<Sport>>, t: Throwable) {
                // Manejar errores de la llamada
            }
        })
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


