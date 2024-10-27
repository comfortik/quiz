package com.example.quizy.presentation.games.models

import com.example.quizy.presentation.common.BaseState

data class GamesUiState(
    val isLoading: Boolean,
    val games: List<GameItemUiState>
): BaseState