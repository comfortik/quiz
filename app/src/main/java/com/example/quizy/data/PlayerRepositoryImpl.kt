package com.example.quizy.data

import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.repositories.PlayerRepository
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PlayerRepositoryImpl @Inject constructor(
    val supabase: SupabaseClientProvider
): PlayerRepository {
    @OptIn(SupabaseExperimental::class)
    override suspend fun getPlayers(): Flow<List<Player>> =
        supabase.client.from("players").selectAsFlow(Player::id)
}