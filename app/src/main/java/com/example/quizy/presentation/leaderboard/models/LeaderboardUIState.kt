package com.example.quizy.presentation.leaderboard.models


import com.example.quizy.presentation.common.BaseState
import com.example.quizy.data.Player

data class LeaderboardUIState(
    val players: List<PlayerCardUIState>,
    val isLoading: Boolean
): BaseState