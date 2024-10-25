package com.example.quizy.presentation.leaderboard.models

import android.graphics.Bitmap
import com.example.quizy.presentation.common.CardState

data class PlayerCardUIState(
    val image: ByteArray,
    val name: String="",
    val totalScore: Int = 0,
    val position: Int=0
) : CardState