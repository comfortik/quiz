package com.example.quizy.presentation.choosePlayer

import androidx.lifecycle.viewModelScope
import com.example.quizy.domain.useCases.ChoosePlayerUseCases
import com.example.quizy.presentation.choosePlayer.models.ChoosePlayerAction
import com.example.quizy.presentation.choosePlayer.models.ChoosePlayerUiState
import com.example.quizy.presentation.choosePlayer.models.ChooseUIState
import com.example.quizy.presentation.common.BaseAction
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.presentation.common.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChoosePlayerViewModel @Inject constructor(
    val choosePlayerUseCases: ChoosePlayerUseCases
): BaseViewModel<ChooseUIState, ChoosePlayerAction>() {
    override fun createInitState(): ChooseUIState =
        ChooseUIState(
            isLoading = true,
            players = emptyList()
        )
    fun init(){
        viewModelScope.launch {
            try {
                getPlayerList()
            }catch (e: Exception){
                notifyError(ErrorType.UNKNOWN_ERROR)
            }
        }
    }

    fun setUserIdToSharedPrefs(id: Int){
        choosePlayerUseCases.setUserId(id)
        sendAction(ChoosePlayerAction.GoToLeaderboard)
    }

    private suspend fun getPlayerList(){
        choosePlayerUseCases.getPlayers().map {players->
            players.map { player->
                ChoosePlayerUiState(
                    id = player.id,
                    name = player.name,
                    image = choosePlayerUseCases.getImageFromString(player.image)
                )
            }
        }.collect{
            _currentState.value = screenState.value.copy(
                isLoading = false,
                players = it
            )
        }
    }
}