package com.example.wizard.data.model

data class Bet(
    val teamOne: String,
    val teamTwo: String,
    val market: String,
    val bet: String,
    val odd: Double,
    val userId: String
)