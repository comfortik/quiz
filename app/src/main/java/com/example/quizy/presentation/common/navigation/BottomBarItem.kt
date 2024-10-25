package com.example.quizy.presentation.common.navigation

import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import com.example.quizy.R
import com.example.quizy.presentation.common.Route
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
sealed interface BottomBarItem {

    @get:DrawableRes
    val icon: Int
    val destination: Route

    @Serializable
    data object Games : BottomBarItem {
        override val icon: Int = R.drawable.bottom_game_icon
        override val destination: Route = Routes.Games
    }

    @Serializable
    data object Leaderboard : BottomBarItem {
        override val icon: Int = R.drawable.bottom_leaderboard_icon
        override val destination: Route = Routes.LeaderboardScreen
    }


    @Serializable
    data object RandomGame : BottomBarItem {
        override val icon: Int = R.drawable.bottom_play_item
        override val destination: Route = object : Route{}
    }

    @Serializable
    data object Search : BottomBarItem {
        override val icon: Int = R.drawable.bottom_search_icon
        override val destination: Route = Routes.Search
    }

    @Serializable
    data object Profile : BottomBarItem {
        override val icon: Int = R.drawable.bottom_profile_icon
        override val destination: Route = Routes.Profile
    }


}

val bottomBarItems = listOf(
    BottomBarItem.Games,
    BottomBarItem.Leaderboard,
    BottomBarItem.RandomGame,
    BottomBarItem.Search,
    BottomBarItem.Profile
)