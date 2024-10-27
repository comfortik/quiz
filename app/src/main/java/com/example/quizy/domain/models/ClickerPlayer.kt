package com.example.quizy.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ClickerPlayer(
    val id: Int,
    val name: String,
    val score: Int
)