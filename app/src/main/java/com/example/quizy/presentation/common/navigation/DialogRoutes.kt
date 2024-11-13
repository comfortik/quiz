package com.example.quizy.presentation.common.navigation

import kotlinx.serialization.Serializable

sealed interface DialogRoutes {

    @Serializable
    data class EndGameDialog(val score: Int): DialogRoutes
    @Serializable
    data class ErrorDialog( val errorMessage: String) : DialogRoutes
}