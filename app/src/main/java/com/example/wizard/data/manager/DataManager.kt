import com.example.wizard.data.model.Event
import com.example.wizard.data.model.Sport
import com.example.wizard.data.remote.api.oddsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataManager {

    private val apiService: oddsApiService = ServiceGenerator.createService(oddsApiService::class.java)

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
                    callback(sportsList, null)
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
}