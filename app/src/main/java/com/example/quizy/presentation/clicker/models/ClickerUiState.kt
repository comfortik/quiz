package com.example.quizy.presentation.clicker.models

import com.example.quizy.presentation.common.BaseState

data class ClickerUiState(
    val isloading: Boolean  = true,
    val players : List< ClickerPlayerUiState> = listOf()
): BaseState
