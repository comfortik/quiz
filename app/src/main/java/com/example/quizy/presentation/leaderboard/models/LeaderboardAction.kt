package com.example.quizy.presentation.leaderboard.models

import com.example.quizy.presentation.common.BaseAction
import com.example.quizy.presentation.common.ErrorType

sealed class LeaderboardAction: BaseAction {

    data class ShowErrorMessage(val error: ErrorType): LeaderboardAction()

}