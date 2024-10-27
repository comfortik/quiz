package com.example.quizy.presentation.leaderboard

import androidx.lifecycle.viewModelScope
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.domain.useCases.GetLeadersUseCase
import com.example.quizy.presentation.common.ErrorType
import com.example.quizy.presentation.leaderboard.models.LeaderboardAction
import com.example.quizy.presentation.leaderboard.models.LeaderboardUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.exceptions.HttpRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val getLeadersUseCase: GetLeadersUseCase
) : BaseViewModel<LeaderboardUIState, LeaderboardAction> (){

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
            }catch (e: HttpRequestException){
                notifyError(ErrorType.NETWORK_ERROR)
            }catch (e: HttpRequestTimeoutException){
                notifyError(ErrorType.TIMEOUT_ERROR)
            }catch (e: Exception){
                notifyError(ErrorType.UNKNOWN_ERROR)
            }finally {
                _currentState.value = screenState.value.copy(
                    isLoading = false
                )
            }

        }

}