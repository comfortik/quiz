package com.example.quizy.data.repositoryImpl

import android.util.Log
import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.models.Player
import com.example.quizy.domain.repositories.PlayerRepository
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PlayerRepositoryImpl @Inject constructor(
    val supabase: SupabaseClientProvider,
    val sharedPrefs : SharedPreferensesProvider
): PlayerRepository {
    val tableName = "players"

    private var savedPlayers: List<Player>?  = null

    @OptIn(SupabaseExperimental::class)
    override suspend fun getPlayers(): Flow<List<Player>> =
        supabase.client.from(tableName).selectAsFlow(Player::id)

    override suspend fun updateTotalScore(score: Int) {
        val id = sharedPrefs.getIdFromSharedPrefs()
        Log.d("playerRepoImpl", id.toString())
        val player = getPlayerById(id)
        val newScore = player.total_score+score
        supabase.client.from(tableName).update({
            set("total_score", newScore)
        }){
            filter { eq("id", id) }
        }
    }

    override suspend fun getCurrentPlayer(): Player {
        val id = sharedPrefs.getIdFromSharedPrefs()
        val player = getPlayerById(id)
        return player
    }

    override suspend fun saveStartScore() {
        savedPlayers = supabase.client.from(tableName).select().decodeList<Player>()
    }


    override suspend fun endGame(players: List<Int>): Player {
        val playerList = supabase.client.from(tableName)
            .select()
            .decodeList<Player>()
            .filter {
                players.contains(it.id)
            }.map {player->
                val savePlayer = savedPlayers?.find { it.id == player.id }
                val gameScore = player.total_score - savePlayer!!.total_score
                player.copy(
                    total_score = gameScore
                )
            }
        savedPlayers =null
        return playerList.maxBy { it.total_score }
    }


    private suspend fun getPlayerById(id: Int) =
        supabase.client.from(tableName).select { filter { eq("id", id) } }.decodeSingle<Player>()
}