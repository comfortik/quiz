package com.example.quizy.presentation.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.quizy.presentation.common.BaseState

data class ProfileUiState (
    val isLoading: Boolean = false,
    val name: String="",
    val image: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
    val total_score: String="",
    val hello: String="day"
): BaseState