package com.example.quizy.data.repositoryImpl

import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.models.ClickerPlayer
import com.example.quizy.domain.repositories.ClickerPlayerRepository
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClickerPlayerRepositoryImpl @Inject constructor(
    val supabase: SupabaseClientProvider,
    val sharedPrefsProvider: SharedPreferensesProvider
) : ClickerPlayerRepository {
    val tableName = "clicker_players"

    @OptIn(SupabaseExperimental::class)
    override suspend fun fetchClickerPlayers(): Flow<List<ClickerPlayer>> =
        supabase.client.from(tableName).selectAsFlow(ClickerPlayer::id)

    override suspend fun earnPoints() {
        val currentId = sharedPrefsProvider.getIdFromSharedPrefs()

        val score =getScore(currentId)+1
        supabase.client.from(tableName).update({
            set("score", (score) )
        }){
            filter { eq("id", currentId) }
        }
    }

    override suspend fun leaveGame(): Int {
        val currentId = sharedPrefsProvider.getIdFromSharedPrefs()
        val score = getScore(currentId)
        val n = 0
        supabase.client.from(tableName).update({
            set("score", n )
        }){
            filter { eq("id", currentId) }
        }
        return score
    }


    private suspend fun getScore(id: Int)=
        supabase.client.from(tableName).select { filter { eq("id", id) } }.decodeSingle<ClickerPlayer>().score

}