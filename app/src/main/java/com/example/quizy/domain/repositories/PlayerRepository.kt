package com.example.quizy.domain.repositories

import com.example.quizy.domain.models.Player
import kotlinx.coroutines.flow.Flow



interface PlayerRepository {
    suspend fun getPlayers(): Flow<List<Player>>
    suspend fun updateTotalScore( score: Int)
    suspend fun getCurrentPlayer(): Player
}