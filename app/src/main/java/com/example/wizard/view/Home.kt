package com.example.wizard.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.wizard.R
import com.example.wizard.data.model.Event
import com.example.wizard.presenter.oddsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log
import com.example.wizard.data.model.Sport

class Home : Fragment() {
    private lateinit var apiService: oddsApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener la lista de deportes
        val retrofit= Retrofit.Builder()
            .baseUrl("https://api.the-odds-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(oddsApiService::class.java)
        obtenerDeportes()

        super.onViewCreated(view, savedInstanceState)

        // Obtén el LinearLayout donde deseas agregar el FrameLayout
        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout2)

        // Infla el diseño post_straight.xml
        val postStraightLayout = LayoutInflater.from(requireContext()).inflate(R.layout.post_straight, null)

        // Agrega el diseño inflado al LinearLayout
        linearLayout.addView(postStraightLayout)
    }

    private fun obtenerDeportes() {
        // Llamar a la API para obtener la lista de deportes
        val call: Call<List<Sport>> = apiService.getSports("93431bcf5f7d7994a9a97f41af907539")
        call.enqueue(object : Callback<List<Sport>> {
            override fun onResponse(call: Call<List<Sport>>, response: Response<List<Sport>>) {
                if (response.isSuccessful) {
                    val sports = response.body() ?: emptyList()
                    for (sport in sports) {
                        obtenerEventosPorDeporte(sport.key, sport.title)
                    }
                    Log.d("API_RESPONSE", "Lista de deportes obtenida correctamente: $sports")
                } else {
                    // Manejar error de respuesta
                    Log.e("API_RESPONSE", "Error al obtener la lista de deportes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Sport>>, t: Throwable) {
                // Manejar error de la llamada
                Log.e("API_RESPONSE", "Error al obtener la lista de deportes: ${t.message}")
            }
        })
    }

    private fun obtenerEventosPorDeporte(sportKey: String, sportTitle: String) {
        // Llamar a la API para obtener los eventos del deporte especificado
        val apiKey = "93431bcf5f7d7994a9a97f41af907539"
        val call: Call<List<Event>> = apiService.getEventsForSport(sportKey, apiKey)
        call.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val eventos = response.body() ?: emptyList()
                    val cantidadEventos = eventos.size
                    mostrarDeporteYSusEventos(sportKey, sportTitle, cantidadEventos)
                    Log.d("API_RESPONSE", "Eventos para el deporte $sportTitle obtenidos correctamente: $eventos")
                } else {
                    // Manejar error de respuesta
                    Log.e("API_RESPONSE", "Error al obtener eventos para el deporte $sportTitle: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                // Manejar error de la llamada
                Log.e("API_RESPONSE", "Error al obtener eventos para el deporte $sportTitle: ${t.message}")
            }

        })
    }

    private fun mostrarDeporteYSusEventos(sportKey: String, sportTitle: String, cantidadEventos: Int) {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.linearLayout)
        // Inflar el layout del círculo
        val circleLayout = LayoutInflater.from(requireContext()).inflate(R.layout.games_item, null)
        // Obtener referencias de las vistas dentro del layout del círculo
        val leagueTextView = circleLayout.findViewById<TextView>(R.id.sport)
        val gamesTextView = circleLayout.findViewById<TextView>(R.id.games)
        // Establecer los datos del deporte y la cantidad de eventos en las vistas
        leagueTextView.text = sportTitle
        gamesTextView.text = "$cantidadEventos games"
        // Agregar el layout del círculo al LinearLayout
        if (linearLayout != null) {
            linearLayout.addView(circleLayout)
        }
    }
}