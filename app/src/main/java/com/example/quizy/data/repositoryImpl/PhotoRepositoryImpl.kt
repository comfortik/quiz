package com.example.quizy.data.repositoryImpl

import android.util.Log
import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.repositories.PhotoRepository
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.storage.Bucket
import io.github.jan.supabase.storage.storage
import javax.inject.Inject


class PhotoRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClientProvider
): PhotoRepository {
    override suspend fun getPhotoFromSupabaseStorage(name: String, tableName: String): ByteArray {
        val response = fetchPhoto(name, tableName)
        return response
    }

    override suspend fun getAllPhotosFromBacket(tableName: String, count: Int): List<ByteArray> {
        val bucket = supabase.client.storage.from(tableName).list().take(count/2)
        val cards = bucket.map {
            fetchPhoto(it.name, tableName)
        }
        return cards
    }
    private suspend fun fetchPhoto(name: String, tableName: String) =
         supabase.client.storage.from(tableName).downloadPublic(name)

}
