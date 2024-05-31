import com.example.wizard.data.model.Event
import com.example.wizard.data.model.EventOdds
import com.example.wizard.data.model.Sport
import com.example.wizard.data.remote.api.OddsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataManager {

    private val apiService: OddsApiService = ServiceGenerator.createService(OddsApiService::class.java)

    fun obtenerEventosDesdeAPI(sportKey: String, callback: (List<Event>?, Throwable?) -> Unit) {
        val call = apiService.getEventsForSport(sportKey)
        call.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val events = response.body() ?: emptyList()
                    callback(events, null)
                } else {
                    callback(null, Throwable("Error en la respuesta de la API"))
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                callback(null, t)
            }
            })
        }

    fun obtenerDeportes(callback: (List<Sport>?, Throwable?) -> Unit) {
        val call = apiService.getSports()
        call.enqueue(object : Callback<List<Sport>> {
            override fun onResponse(call: Call<List<Sport>>, response: Response<List<Sport>>) {
                if (response.isSuccessful) {
                    val sportsList = response.body()
                    val filteredSports = sportsList?.filter { sport ->
                        !sport.title.contains("winner", ignoreCase = true)
                    }
                    callback(filteredSports, null)
                } else {
                    callback(null, Throwable("Error en la respuesta de la API"))
                }
            }

            override fun onFailure(call: Call<List<Sport>>, t: Throwable) {
                callback(null, t)
            }
                })
        }
    fun obtenerCantidadEventosPorDeporte(sportKey: String, callback: (Int?, Throwable?) -> Unit) {
        val call = apiService.getEventsForSport(sportKey)
        call.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val eventsList = response.body()
                    val eventCount = eventsList?.size ?: 0
                    callback(eventCount, null)
                } else {
                    callback(null, Throwable("Error en la respuesta de la API"))
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                callback(null, t)
            }
        })
    }
    fun obtenerEventOdds(sportKey: String, eventId: String, callback: (EventOdds?, Throwable?) -> Unit) {
        val call = apiService.getEventOdds(sportKey, eventId, "draftkings", "h2h,spreads,totals")
        call.enqueue(object : Callback<EventOdds> {
            override fun onResponse(call: Call<EventOdds>, response: Response<EventOdds>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Throwable(response.message()))
                }
            }

            override fun onFailure(call: Call<EventOdds>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}