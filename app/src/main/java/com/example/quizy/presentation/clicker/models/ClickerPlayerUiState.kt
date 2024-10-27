package com.example.quizy.presentation.clicker.models

import com.example.quizy.presentation.common.BaseState

data class ClickerPlayerUiState(
    val name: String,
    val score: Int
):BaseState
