package com.example.quizy.presentation.quiz.models

import com.example.quizy.presentation.common.LeaderDTO
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
    val isStarted: Boolean = false,
    @SerialName("current_question")
    val currentQuestion: Int = 0,
    @SerialName("leader")
    val leader: LeaderDTO? = null,
    @SerialName("is_ended")
    val isEnded: Boolean? = false

)