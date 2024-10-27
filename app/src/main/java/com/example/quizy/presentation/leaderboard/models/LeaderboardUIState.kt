package com.example.quizy.presentation.leaderboard.models


import com.example.quizy.presentation.common.BaseState

data class LeaderboardUIState(
    val players: List<PlayerCardUIState>,
    val isLoading: Boolean
): BaseState