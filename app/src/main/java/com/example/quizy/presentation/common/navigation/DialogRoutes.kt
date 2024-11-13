package com.example.quizy.presentation.common.navigation

import kotlinx.serialization.Serializable

sealed interface DialogRoutes {

    @Serializable
    data class EndGameDialog(val name: String="", val score: Int): DialogRoutes
    @Serializable
    data class ErrorDialog( val errorMessage: String) : DialogRoutes
}