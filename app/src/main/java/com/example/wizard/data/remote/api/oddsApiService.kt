package com.example.wizard.data.remote.api

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
    fun getSports(): Call<List<Sport>>

    @GET("v4/sports/{sport}/events")
    fun getEventsForSport(
        @Path("sport") sportKey: String
    ): Call<List<Event>>

    @GET("v4/sports/{sport}/events/{eventId}/odds")
    fun getEventById(
        @Path("sport") sportKey: String,
        @Path("event") eventKey: String
    ): Call<List<Match>>

    @GET("/v4/sports/{sport}/events/{eventId}/odds")
    fun getEventOdds(
        @Path("sport") sportKey: String,
        @Path("event") eventId: String,
        @Query("regions") regions: String,
        @Query("markets") markets: String,
        @Query("dateFormat") dateFormat: String,
        @Query("oddsFormat") oddsFormat: String
    ):Call<EventOdds>
}