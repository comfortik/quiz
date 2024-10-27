package com.example.quizy.presentation.choosePlayer.models

import com.example.quizy.presentation.common.BaseAction

sealed class ChoosePlayerAction: BaseAction {
    data object GoToLeaderboard: ChoosePlayerAction()
}