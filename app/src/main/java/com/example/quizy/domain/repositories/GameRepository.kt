package com.example.quizy.domain.repositories

import com.example.quizy.domain.models.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun fetchGames(): List<Game>
}