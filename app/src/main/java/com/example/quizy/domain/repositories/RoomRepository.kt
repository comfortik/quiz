package com.example.quizy.domain.repositories

import com.example.quizy.domain.models.Player
import com.example.quizy.presentation.quiz.models.QuizRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RoomRepository {
    suspend fun createRoom():Int
    suspend fun joinRoom(roomId: Int): Int
    suspend fun updateReadyPlayers()
    suspend fun updateReadyPlayersDown()
    suspend fun subRoom(): Flow<QuizRoom>
    suspend fun nextQuestion(num: Int)
    suspend fun startGame()
    suspend fun setLeader(player: Player)
    suspend fun endGame()
}