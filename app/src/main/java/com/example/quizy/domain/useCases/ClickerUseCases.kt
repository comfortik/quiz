package com.example.quizy.domain.useCases

import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.domain.repositories.ClickerPlayerRepository
import com.example.quizy.domain.repositories.PlayerRepository
import com.example.quizy.presentation.clicker.models.ClickerPlayerUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClickerUseCases @Inject constructor(
    val playerRepository: PlayerRepository,
    val clickerPlayerRepository: ClickerPlayerRepository,
    val sharedPrefs: SharedPreferensesProvider
) {
    suspend fun fetchPlayers(): Flow<List<ClickerPlayerUiState>> =
        clickerPlayerRepository.fetchClickerPlayers().map { players->
            val id = sharedPrefs.getIdFromSharedPrefs()
            players.sortedByDescending {
                when(it.id){
                    id-> Int.MAX_VALUE
                    else-> it.score
                }
            }.map { player->
                ClickerPlayerUiState(
                    name = player.name,
                    score = player.score
                )
            }
        }


    suspend fun earnPoints(){
        clickerPlayerRepository.earnPoints()
        playerRepository.updateTotalScore( 1)
    }
    suspend fun leaveGame(id: Int){
        clickerPlayerRepository.leaveGame()
    }

}