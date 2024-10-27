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

    @OptIn(SupabaseExperimental::class)
    override suspend fun getPlayers(): Flow<List<Player>> =
        supabase.client.from(tableName).selectAsFlow(Player::id)

    override suspend fun updateTotalScore(score: Int) {
        val id = sharedPrefs.getIdFromSharedPrefs()
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


    private suspend fun getPlayerById(id: Int) =
        supabase.client.from(tableName).select { filter { eq("id", id) } }.decodeSingle<Player>()
}