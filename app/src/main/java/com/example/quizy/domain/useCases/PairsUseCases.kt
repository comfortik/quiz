package com.example.quizy.domain.useCases

import com.example.quizy.domain.repositories.PhotoRepository
import com.example.quizy.domain.repositories.PlayerRepository
import javax.inject.Inject

class PairsUseCases @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val playerRepository: PlayerRepository
) {
    val tableName = "find_couple"
    suspend fun fetchCards(count: Int)=
        photoRepository.getAllPhotosFromBacket(tableName, count)

    suspend fun updateScore(score:Int){
        playerRepository.updateTotalScore(score)
    }

}