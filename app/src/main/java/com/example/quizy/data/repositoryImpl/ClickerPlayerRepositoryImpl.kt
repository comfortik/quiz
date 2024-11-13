package com.example.quizy.data.repositoryImpl

import android.util.Log
import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.models.ClickerPlayer
import com.example.quizy.domain.repositories.ClickerPlayerRepository
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class ClickerPlayerRepositoryImpl @Inject constructor(
    val supabase: SupabaseClientProvider,
    val sharedPrefsProvider: SharedPreferensesProvider
) : ClickerPlayerRepository {
    val tableName = "clicker_players"
    val mutex = Mutex()
    private  var currentId = -1


    init {
        if(currentId==-1){
            currentId = sharedPrefsProvider.getIdFromSharedPrefs()
        }
    }
    @OptIn(SupabaseExperimental::class)
    override suspend fun fetchClickerPlayers(): Flow<List<ClickerPlayer>> =
        supabase.client.from(tableName).selectAsFlow(ClickerPlayer::id)

    override suspend fun earnPoints() {


        mutex.withLock {
            Log.d("repo", currentId.toString())
            val score =getScore(currentId)+1
            supabase.client.from(tableName).update({
                set("score", (score))
            }) {
                filter { eq("id", currentId) }
            }
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