package com.example.quizy.presentation.utlis

import android.graphics.BitmapFactory

fun ByteArrayToBitmapUtil(bytes :ByteArray) =
    BitmapFactory.decodeStream(bytes.inputStream())