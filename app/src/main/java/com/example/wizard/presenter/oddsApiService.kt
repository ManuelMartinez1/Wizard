package com.example.wizard.presenter
import com.example.wizard.data.model.Event
import com.example.wizard.data.model.EventOdds
import com.example.wizard.data.model.Match
import com.example.wizard.data.model.Sport
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

    @GET("v4/sports/{sport}/events/{eventId}/odds")
    fun getEventById(
        @Path("sport") sportKey: String,
        @Path("event") eventKey: String,
        @Query("apiKey") apiKey: String
    ): Call<List<Match>>

    @GET("/v4/sports/{sport}/events/{eventId}/odds")
    fun getEventOdds(
        @Query("apiKey") apiKey: String,
        @Query("regions") regions: String,
        @Query("markets") markets: String,
        @Query("dateFormat") dateFormat: String,
        @Query("oddsFormat") oddsFormat: String,
        @Path("sport") sportKey: String,
        @Path("event") eventId: String,
    ): Call<EventOdds>

    }




