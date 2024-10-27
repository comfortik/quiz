package com.example.quizy.presentation.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.quizy.presentation.common.BaseAction
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.presentation.common.ErrorType
import com.example.quizy.presentation.profile.models.ProfileUseCases
import com.example.quizy.presentation.utlis.ByteArrayToBitmapUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.exceptions.HttpRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : BaseViewModel<ProfileUiState, BaseAction>() {
    override fun createInitState(): ProfileUiState =
        ProfileUiState(
            isLoading = true
        )


    fun init(){
        viewModelScope.launch {
            try{
                getPlayer()
            }catch (e: HttpRequestException){
                notifyError(ErrorType.NETWORK_ERROR)
            }catch (e: HttpRequestTimeoutException){
                notifyError(ErrorType.TIMEOUT_ERROR)
            }catch (e: Exception){
                notifyError(ErrorType.UNKNOWN_ERROR)
            }finally {
                _currentState.value = screenState.value.copy(
                    isLoading = false
                )
            }

        }
    }


    private suspend fun getPlayer(){
        val player =  profileUseCases.fetchPlayer()
        val image = profileUseCases.getImage(player.image)
        val hello = getHours()

        _currentState.value = screenState.value.copy(
            isLoading = false,
            name = player.name,
            image = ByteArrayToBitmapUtil(image),
            total_score = player.total_score.toString(),
            hello = hello
        )
    }
    private fun getHours(): String{
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        return  when(hour){
            in 12..18 -> "day"
            in 18..22 -> "evening"
            in 22..23, in 0..4 -> "night"
            in 4..12 -> "morning"
            else->""
        }
    }
}