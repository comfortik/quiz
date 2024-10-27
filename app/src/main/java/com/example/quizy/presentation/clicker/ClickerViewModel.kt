package com.example.quizy.presentation.clicker

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.quizy.domain.useCases.ClickerUseCases
import com.example.quizy.presentation.clicker.models.ClickerUiState
import com.example.quizy.presentation.common.BaseAction
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.presentation.common.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClickerViewModel  @Inject constructor(
    val clickerUseCases: ClickerUseCases
): BaseViewModel<ClickerUiState, BaseAction>() {
    override fun createInitState(): ClickerUiState =
        ClickerUiState(
            isloading = true,
            players = emptyList()
        )

    fun init(){
       viewModelScope.launch {
           try{
               fetchPlayers()
           }catch (e: Exception){
               Log.d("error viewmodel", e.cause.toString())
               notifyError(ErrorType.UNKNOWN_ERROR)
           }
       }
    }
    private suspend fun fetchPlayers(){
        clickerUseCases.fetchPlayers().collect{
            _currentState.value =screenState.value.copy(
                isloading = false,
                players = it
            )
        }

    }
    fun earnPoint(){
        viewModelScope.launch {
            clickerUseCases.earnPoints()
        }
    }
}