package com.example.quizy.domain.useCases

import com.example.quizy.domain.repositories.GameRepository
import com.example.quizy.domain.repositories.PhotoRepository
import com.example.quizy.presentation.games.models.GameItemUiState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameUseCases @Inject constructor(
    val gameRepository: GameRepository    ,
    val photoRepository: PhotoRepository
) {
    suspend fun fetchGames() = gameRepository.fetchGames()

    suspend fun getPhotoFromSupabase(image: String) =
        photoRepository.getPhotoFromSupabaseStorage(image, "images_draw")

}