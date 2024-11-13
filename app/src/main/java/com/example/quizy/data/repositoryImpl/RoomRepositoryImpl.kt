package com.example.quizy.data.repositoryImpl

import android.util.Log
import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.repositories.RoomRepository
import com.example.quizy.presentation.quiz.models.QuizRoom
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class RoomRepositoryImpl(
    private val supabase: SupabaseClientProvider,
    private val sharedPrefs: SharedPreferensesProvider
): RoomRepository {
    private var currentId = -1
    private val tableName= "room"
    private var roomId = -1
    init{
        if(currentId==-1){
            currentId= sharedPrefs.getIdFromSharedPrefs()
        }
    }

    override suspend fun createRoom(): Int {
            Log.d("repo", "stratcreate")
            val room = supabase.client.from(tableName).insert(QuizRoom(playerIds = listOf(currentId), readyPlayerIds = listOf())){
                    select()
            }.decodeSingle<QuizRoom>()
            if (roomId==-1){
                roomId =room.id?:-1
            }

            return roomId

    }

    override suspend fun joinRoom(roomId: Int): Int {
        val room = supabase.client.from(tableName).select { filter { eq("id", roomId) } }.decodeSingle<QuizRoom>()
        val newPlayers = room.playerIds.toMutableList()
        newPlayers.add(currentId)
        if(!room.isStarted){
            return -1
        }
        if(this.roomId==-1){
            this.roomId= roomId
        }
        updatePlayers(newPlayers)
        return roomId
    }


    private suspend fun updatePlayers(players: List<Int>){
        val set = players.toSet()
        supabase.client.from(tableName).update({
            set("players", set)
        }){
            filter { eq("id", roomId) }
        }
    }

    override suspend fun updateReadyPlayers() {
        val oldPlayers = getQuizRoom().readyPlayerIds
        val newPlayers = oldPlayers?.toMutableList()
        newPlayers?.add(currentId)
        supabase.client.from(tableName).update({set("ready_players", newPlayers)}){
            filter { eq("id", roomId) }
        }
    }

    override suspend fun updateReadyPlayersDown() {
        val oldPlayers = getQuizRoom().readyPlayerIds
        val newPlayers = oldPlayers?.toMutableList()
        newPlayers?.remove(currentId)
        supabase.client.from(tableName).update({set("ready_players", newPlayers)}){
            filter { eq("id", roomId) }
        }
    }

    override suspend fun updatePlayers() {
        val oldPlayers = getQuizRoom().playerIds

    }

    private suspend fun getQuizRoom(): QuizRoom{
        val room = supabase.client.from(tableName).select(){
            filter { eq("id", roomId) }
        }.decodeSingle<QuizRoom>()
        return room
    }

    @OptIn(SupabaseExperimental::class)
    override suspend fun getPlayers(): Flow<Set<Int>> {
        Log.d("repo", "start getPlayers")

        val filter = FilterOperation(
            column = "id",
            operator = FilterOperator.EQ,
            value = roomId
        )
        val list = supabase.client.from(tableName)

        Log.d("repo", list.select().decodeList<QuizRoom>().toString())

        val a = list.selectAsFlow(QuizRoom::id, filter = filter)

        return a.map { rooms ->
            if (rooms.isNotEmpty()) {
                Log.d("repo", "Found room: ${rooms[0].id}")
                rooms[0].playerIds
            } else {
                Log.d("repo", "No rooms found for roomId: $roomId")
                emptyList()
            }.toSet()
        }
    }

    @OptIn(SupabaseExperimental::class)
    override suspend fun getReadyPlayers(): Flow<Set<Int>> {
        val filter = FilterOperation(
            column = "id",
            operator = FilterOperator.EQ,
            value = roomId
        )
        val list = supabase.client.from(tableName)

        Log.d("repo", list.select().decodeList<QuizRoom>().toString())

        val a = list.selectAsFlow(QuizRoom::id, filter = filter)

        return a.map { rooms ->
            if (rooms.isNotEmpty()) {
                Log.d("repo", "Found room: ${rooms[0].id}")
                rooms[0].readyPlayerIds
            } else {
                Log.d("repo", "No rooms found for roomId: $roomId")
                emptyList()
            }!!.toSet()

        }
    }

    override suspend fun getCurrentQuestion(): StateFlow<Int> {
        TODO("Not yet implemented")
    }
}