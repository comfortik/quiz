package com.example.quizy.presentation.common

import kotlinx.serialization.Serializable

@Serializable
data class LeaderDTO (
    val name: String,
    val score: Int
)