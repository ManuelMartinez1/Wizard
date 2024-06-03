package com.example.wizard.data.model

data class Bet(
    val userName: String,
    val bet: String,
    val market: String,
    val odd: Double,
    val teamOne: String,
    val teamTwo: String,
    val userId: String
)