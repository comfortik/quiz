package com.example.quizy.presentation.quiz.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizRoom(
    val id: Int?=null,
    @SerialName("created_at")
    val timeStamp: String?=null,
    @SerialName("players")
    val playerIds: List<Int>,
    @SerialName("ready_players")
    val readyPlayerIds: List<Int>? =null,
    @SerialName("is_started")
    val isStarted: Boolean = false
)