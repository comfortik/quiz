package com.example.quizy.presentation.games

import androidx.lifecycle.viewModelScope
import com.example.quizy.domain.useCases.GameUseCases
import com.example.quizy.presentation.common.BaseAction
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.presentation.common.ErrorType
import com.example.quizy.presentation.games.models.GameItemUiState
import com.example.quizy.presentation.games.models.GamesActions
import com.example.quizy.presentation.games.models.GamesUiState
import com.example.quizy.presentation.utlis.ByteArrayToBitmapUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.exceptions.HttpRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    val gameUseCases: GameUseCases,
): BaseViewModel<GamesUiState, BaseAction>() {
    override fun createInitState(): GamesUiState =
        GamesUiState(
            isLoading = true,
            games = emptyList()
        )

    fun init(){
        viewModelScope.launch {
//            try{
                fetchGames()
//            }catch (e: HttpRequestException){
//                notifyError(ErrorType.NETWORK_ERROR)
//            }catch (e: HttpRequestTimeoutException){
//                notifyError(ErrorType.TIMEOUT_ERROR)
//            }catch (e: Exception){
//                notifyError(ErrorType.UNKNOWN_ERROR)
//            }finally {
//                _currentState.value = screenState.value.copy(
//                    isLoading = false
//                )
//            }
        }
    }

    private suspend fun fetchGames(){
        val gameList = gameUseCases.fetchGames() .map {
            val imageBytes =gameUseCases.getPhotoFromSupabase(it.image)
            GameItemUiState(
                name = it.name,
                image = ByteArrayToBitmapUtil(imageBytes)
            )
        }


        _currentState.value = screenState.value.copy(
            isLoading = false,
            games = gameList
        )
    }
}