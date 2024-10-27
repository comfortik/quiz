package com.example.quizy.data.repositoryImpl

import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.models.Game
import com.example.quizy.domain.repositories.GameRepository
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    val supabase: SupabaseClientProvider
): GameRepository {
    private val tableName = "games"

    override suspend fun fetchGames()=
        supabase.client.from(tableName).select().decodeList<Game>()
}