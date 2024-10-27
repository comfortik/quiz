package com.example.quizy.domain.repositories

import android.graphics.Bitmap

interface PhotoRepository {
    suspend fun getPhotoFromSupabaseStorage(name: String, tableName: String): ByteArray
    suspend fun getAllPhotosFromBacket(tableName: String, count: Int): List<ByteArray>
}