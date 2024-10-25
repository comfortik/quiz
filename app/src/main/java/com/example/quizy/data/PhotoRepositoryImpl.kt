package com.example.quizy.data

import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.repositories.PhotoRepository
import io.github.jan.supabase.storage.storage
import javax.inject.Inject


class PhotoRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClientProvider
): PhotoRepository {
    override suspend fun getPhotoFromSupabaseStorage(name: String): ByteArray {
        val response = supabase.client.storage.from("avatars").downloadPublic("$name.png")
        println("Successfully downloaded photo: $name")
        return response
    }
}
