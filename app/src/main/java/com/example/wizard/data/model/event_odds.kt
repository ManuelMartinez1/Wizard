package com.example.wizard.data.model

data class EventOdds(
    val id: String,
    val sportKey: String,
    val sportTitle: String,
    val commenceTime: String,
    val homeTeam: String,
    val awayTeam: String,
    val bookmakers: List<Bookmaker>
)

data class Bookmaker(
    val key: String,
    val title: String,
    val markets: List<Market>
)

data class Market(
    val key: String,
    val lastUpdate: String,
    val outcomes: List<Outcome>
)

data class Outcome(
    val name: String,
    val description: String,
    val price: Double,
    val point: Double
)
