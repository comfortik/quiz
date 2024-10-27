package com.example.quizy.presentation.pairs.model

import android.graphics.Bitmap
import android.graphics.Color

data class CardUiState(
    val num: Int =-1,
    val id: Int = -1,
    var isVisible: Boolean = false,
    val image : Bitmap =  Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
        eraseColor(Color.WHITE)
    }
)
