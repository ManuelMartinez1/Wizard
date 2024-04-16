package com.example.wizard.presenter
import com.example.wizard.model.Event
import com.example.wizard.view.Sport
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface oddsApiService {
    @GET("v4/sports")
    fun getSports(@Query("apiKey") apiKey: String): Call<List<Sport>>

    @GET("v4/sports/{sport}/events")
    fun getEventsForSport(
        @Path("sport") sportKey: String,
        @Query("apiKey") apiKey: String
    ): Call<List<Event>>

}

