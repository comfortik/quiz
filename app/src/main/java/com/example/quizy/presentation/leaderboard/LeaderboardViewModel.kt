package com.example.quizy.presentation.leaderboard

import androidx.lifecycle.viewModelScope
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.domain.GetLeadersUseCase
import com.example.quizy.presentation.common.ErrorType
import com.example.quizy.presentation.leaderboard.models.LeaderboardAction
import com.example.quizy.presentation.leaderboard.models.LeaderboardUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val getLeadersUseCase: GetLeadersUseCase
) : BaseViewModel<LeaderboardUIState, LeaderboardAction>() {

    override fun createInitState(): LeaderboardUIState =
        LeaderboardUIState(
            players = listOf(),
            isLoading = true
        )

    fun init(){
        getPlayerList()
    }


    private fun getPlayerList()=
        viewModelScope.launch {
            try{
                getLeadersUseCase().collect{players->
                    _currentState.value = screenState.value.copy(
                        isLoading = false,
                        players = players
                    )

                }
            }catch (e: IOException){
                sendAction(LeaderboardAction.ShowErrorMessage(ErrorType.NETWORK_ERROR))
            }catch (e: TimeoutException){
                sendAction(LeaderboardAction.ShowErrorMessage(ErrorType.TIMEOUT_ERROR))
            }catch (e: Exception){
                sendAction(LeaderboardAction.ShowErrorMessage(ErrorType.UNKNOWN_ERROR))
            }finally {
                _currentState.value = screenState.value.copy(
                    isLoading = false
                )
            }

        }

}