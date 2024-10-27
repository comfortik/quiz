package com.example.quizy.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val name: String,
    val image: String
)