package com.example.quizy.presentation.common.navigation

import com.example.quizy.presentation.common.Route
import kotlinx.serialization.Serializable



@Serializable
sealed class Routes: Route {
    @Serializable
    data object LeaderboardScreen: Route
    @Serializable
    data class ErrorDialog( val errorMessage: String) : Route
    @Serializable
    data object Clicker : Route
    @Serializable
    data object Games : Route
    @Serializable
    data object Pairs : Route
    @Serializable
    data object Profile : Route
    @Serializable
    data object Search : Route
    @Serializable
    data object Quiz : Route
    @Serializable
    data object Drawing : Route
    @Serializable
    data object ChoosePlayer: Route

}