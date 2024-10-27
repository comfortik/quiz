package com.example.quizy.domain.repositories

import com.example.quizy.domain.models.ClickerPlayer
import kotlinx.coroutines.flow.Flow

interface ClickerPlayerRepository {
    suspend fun fetchClickerPlayers(): Flow<List<ClickerPlayer>>
    suspend fun earnPoints()
    suspend fun leaveGame():Int
}