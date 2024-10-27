package com.example.quizy.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Player(
    val id: Int,
    val name: String,
    val total_score: Int,
    val image: String
)
