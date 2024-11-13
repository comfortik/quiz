package com.example.quizy.presentation.common.navigation

import com.example.quizy.presentation.common.Route
import kotlinx.serialization.Serializable



@Serializable
sealed interface Routes:Route {
    @Serializable
    data object LeaderboardScreen: Routes
    @Serializable
    data object Clicker : Routes
    @Serializable
    data object Games : Routes
    @Serializable
    data object Pairs : Routes
    @Serializable
    data object Profile : Routes
    @Serializable
    data object Search : Routes
    @Serializable
    data object Quiz : Routes
    @Serializable
    data object Drawing : Routes
    @Serializable
    data object ChoosePlayer: Routes


}