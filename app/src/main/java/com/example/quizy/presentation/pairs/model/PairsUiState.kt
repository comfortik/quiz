package com.example.quizy.presentation.pairs.model

import com.example.quizy.presentation.common.BaseState

data class PairsUiState(
    val isLoading: Boolean = true,
    val cards: List<CardUiState> = listOf(),
    val count: Int=9,
    val score: Int=0,
    val timer: String=""
): BaseState