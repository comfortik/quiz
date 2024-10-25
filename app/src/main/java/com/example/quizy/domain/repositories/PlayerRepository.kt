package com.example.quizy.domain.repositories

import com.example.quizy.data.Player
import kotlinx.coroutines.flow.Flow



interface PlayerRepository {
    suspend fun getPlayers(): Flow<List<Player>>
}