package com.example.quizy.presentation.games.models

import com.example.quizy.presentation.common.BaseAction

sealed class GamesActions: BaseAction {
    data class NavigateToGame(val game: String): GamesActions()
}