package com.example.quizy.presentation.choosePlayer.models

import com.example.quizy.presentation.common.BaseState


data class ChooseUIState (
    val isLoading: Boolean ,
    val players: List< ChoosePlayerUiState>
): BaseState
