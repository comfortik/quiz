package com.example.quizy.presentation.pairs

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.quizy.domain.useCases.PairsUseCases
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.presentation.pairs.model.CardUiState
import com.example.quizy.presentation.pairs.model.PairsAction
import com.example.quizy.presentation.pairs.model.PairsUiState
import com.example.quizy.presentation.utlis.ByteArrayToBitmapUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PairsViewModel @Inject constructor(
    private val pairsUseCases: PairsUseCases
): BaseViewModel<PairsUiState, PairsAction>() {

    private var saveCard = -10
    private var endCards  = mutableListOf<Int>()

    override fun createInitState(): PairsUiState =
        PairsUiState(
            isLoading = true,
            cards = mutableListOf(),
            count = 9,
            score = 0,
            timer = "00:00"
        )

    fun init(){
        viewModelScope.launch {
            fetchCards(screenState.value.count) //TODO try catch
        }
    }

    private fun convertImages(bytesList: List<ByteArray>): List<CardUiState> {
        val cards = bytesList.mapIndexedNotNull { index, bytes ->
            val bitmap = ByteArrayToBitmapUtil(bytes)
            if (bitmap != null) {
                CardUiState(
                    id = index,
                    image = bitmap,
                    isVisible = false
                )
            } else {
                Log.e("PairsViewModel", "Failed to convert ByteArray to Bitmap at index $index")
                null
            }
        }


        val cardsTwo = cards.shuffled()
        val res = cards + cardsTwo


        return res
    }

    private suspend fun updateScore(count: Int){
        pairsUseCases.updateScore(count)
        val newScore = screenState.value.score+count
        _currentState.value = screenState.value.copy(
            score = newScore
        )
    }

    fun checkCard(num: Int, id: Int){
        flipOneCard(num)
        if(!endCards.contains(num)){
            if (saveCard!=-10 ){
                if (saveCard==id){
                    endCards.add(id)
                    viewModelScope.launch {
                        updateScore(50)
                    }
                }
                viewModelScope.launch {
                    delay(500)
                    flipCards()
                    saveCard =-10
                }
            } else if(id==-1){
                endCards.add(id)
            } else{
                saveCard = id
            }
        }
        Log.d("sada", "${endCards.size}, ${screenState.value.cards.size}")
        if(endCards.size>screenState.value.cards.size/2){
            endGame(screenState.value.score+50)
        }

    }

    private fun endGame(score: Int){
        sendAction(PairsAction.EndGameDialog(score))
    }

    private fun flipOneCard(num: Int){
        val cards = screenState.value.cards.map { card->
            if(card.num==num){
                card.copy(
                    isVisible = true
                )
            }else card
        }
        _currentState.value = screenState.value.copy(
            cards = cards
        )
    }


    private fun flipCards(){
        val cards = screenState.value.cards.map { card->
            if(!endCards.contains(card.id)){
                card.copy(
                    isVisible = false
                )
            }else card
        }
        _currentState.value = screenState.value.copy(
            cards = cards
        )
    }






    private suspend fun fetchCards(count: Int){
        val cardsImages =  pairsUseCases.fetchCards(count)
        val cards = convertImages(cardsImages).mapIndexed{index, card->
            card.copy(
                num = index
            )
        }.toMutableList()
        if(count%2!=0){
            cards.add(CardUiState(num = count))
        }
        cards.shuffle()
        Log.d("cards", cards.joinToString())

        _currentState.value = screenState.value.copy(
            isLoading = false,
            cards = cards
        )
    }
}