package com.example.quizy.data.repositoryImpl

import android.util.Log
import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.models.Player
import com.example.quizy.domain.repositories.RoomRepository
import com.example.quizy.presentation.common.LeaderDTO
import com.example.quizy.presentation.quiz.models.QuizRoom
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.json.JSONObject

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
            val room = supabase.client
                .from(tableName)
                .insert(
                    QuizRoom(
                        playerIds = listOf(currentId),
                        readyPlayerIds = listOf(),
                        isEnded = false
                    )
                ){
                    select()
            }.decodeSingle<QuizRoom>()
            if (roomId==-1){
                roomId =room.id?:-1
            }

            return roomId

    }

    override suspend fun joinRoom(roomId: Int): Int {
        val room = supabase.client.from(tableName).select { filter { eq("id", roomId) } }.decodeSingle<QuizRoom>()

        if(!room.isStarted){
            val newPlayers = room.playerIds.toMutableList()
            newPlayers.add(currentId)
            if(this.roomId==-1){
                this.roomId= roomId
            }
            updatePlayers(newPlayers)
            return roomId
        }else{
            return -1
        }

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
        Log.d("ready repo", "asdas")
        val oldPlayers = getQuizRoom().readyPlayerIds
        Log.d("ready repo", "$oldPlayers")
        val newPlayers = oldPlayers?.toMutableList()
        newPlayers?.add(currentId)
        Log.d("ready repo", "$newPlayers")
        supabase.client.from(tableName).update({set("ready_players", newPlayers?.toSet())}){
            filter { eq("id", roomId) }
        }
    }

    override suspend fun updateReadyPlayersDown() {
        val oldPlayers = getQuizRoom().readyPlayerIds
        val newPlayers = oldPlayers?.toMutableList()
        newPlayers?.remove(currentId)
        Log.d("repo", "${newPlayers?.joinToString()}")
        supabase.client.from(tableName).update({set("ready_players", listOf<Int>())}){
            filter { eq("id", roomId) }
        }
    }



    private suspend fun getQuizRoom(): QuizRoom{
        val room = supabase.client.from(tableName).select(){
            filter { eq("id", roomId) }
        }.decodeSingle<QuizRoom>()
        return room
    }


    @OptIn(SupabaseExperimental::class)
    override suspend fun subRoom(): Flow<QuizRoom> {

        val filter = FilterOperation(
            column = "id",
            operator = FilterOperator.EQ,
            value = roomId
        )
        val flow = supabase.client
            .from(tableName)
            .selectAsFlow(QuizRoom::id, filter = filter)

        val room = flow.map{it.first()}
        return room
    }

    override suspend fun nextQuestion(num: Int) {
        Log.d("repo", "next question $num")
        supabase.client.from(tableName).update({
            set("current_question", num)
        }){
            filter { eq("id", roomId) }
        }
    }

    override suspend fun startGame() {
        supabase.client.from(tableName).update({set("is_started", true)}){filter { eq("id", roomId) }}
    }

    override suspend fun setLeader(player: Player) {
        val leaderDTO = LeaderDTO(player.name, player.total_score)
        supabase.client.from(tableName).update({set("leader", leaderDTO)}){
            filter {    eq("id", roomId) }
        }
    }

    override suspend fun endGame(){
        supabase.client.from(tableName).update({
            set("is_ended", true)
        }){
            filter { eq("id", roomId) }
        }
    }


}