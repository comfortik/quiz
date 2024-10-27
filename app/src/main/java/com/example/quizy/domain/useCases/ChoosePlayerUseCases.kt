package com.example.quizy.domain.useCases

import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.domain.repositories.PhotoRepository
import com.example.quizy.domain.repositories.PlayerRepository
import com.example.quizy.presentation.choosePlayer.models.ChoosePlayerUiState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChoosePlayerUseCases  @Inject constructor(
    val playerRepository: PlayerRepository,
    val  photoRepository: PhotoRepository,
    val sharedPrefs: SharedPreferensesProvider
) {
    suspend fun getPlayers()=
        playerRepository.getPlayers()

    suspend fun getImageFromString(image: String)=
        photoRepository.getPhotoFromSupabaseStorage(image, "avatars")


    fun setUserId(id: Int){
        sharedPrefs.setIdToSharedPrefs(id)
    }


}