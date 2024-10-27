package com.example.quizy.presentation.profile.models

import com.example.quizy.domain.models.Player
import com.example.quizy.domain.repositories.PhotoRepository
import com.example.quizy.domain.repositories.PlayerRepository

class ProfileUseCases (
    val playerRepository: PlayerRepository,
    val photoRepository: PhotoRepository
){
    suspend fun fetchPlayer(): Player =
        playerRepository.getCurrentPlayer()

    suspend fun getImage(name: String) =
        photoRepository.getPhotoFromSupabaseStorage(name, "avatars")
}
