package com.example.quizy.data

import kotlinx.serialization.Serializable


@Serializable
data class Player(
    val id: Int,
    val name: String,
    val total_score: Int,
    val image: String
)
