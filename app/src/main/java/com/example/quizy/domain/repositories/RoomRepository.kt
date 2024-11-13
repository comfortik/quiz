package com.example.quizy.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RoomRepository {
    suspend fun createRoom():Int
    suspend fun joinRoom(roomId: Int): Int
    suspend fun updateReadyPlayers()
    suspend fun updateReadyPlayersDown()
    suspend fun updatePlayers()
    suspend fun getPlayers(): Flow<Set<Int>>
    suspend fun getReadyPlayers(): Flow<Set<Int>>
    suspend fun getCurrentQuestion(): StateFlow<Int>
}