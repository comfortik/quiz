package com.example.quizy.domain

import com.example.quizy.domain.repositories.PhotoRepository
import com.example.quizy.domain.repositories.PlayerRepository
import com.example.quizy.presentation.leaderboard.models.PlayerCardUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject



class GetLeadersUseCase @Inject constructor(
    val playerRepository: PlayerRepository,
    val photoRepository : PhotoRepository
) {

    suspend operator fun invoke(): Flow<List<PlayerCardUIState>> {
        return playerRepository.getPlayers().map { players ->
            players.sortedByDescending { it.total_score }.mapIndexed { index, player ->
                PlayerCardUIState(
                    name = player.name,
                    totalScore = player.total_score,
                    position = index,
                    image = loadImage(player.image)
                )
            }
        }
    }
    private suspend fun loadImage(name: String)=
        photoRepository.getPhotoFromSupabaseStorage(name)





}