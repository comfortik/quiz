package com.example.quizy.presentation.quiz

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.quizy.domain.useCases.QuizUseCases
import com.example.quizy.presentation.common.BaseAction
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.presentation.quiz.models.QuestionWithAnswer
import com.example.quizy.presentation.quiz.models.QuizAction
import com.example.quizy.presentation.quiz.models.QuizIntent
import com.example.quizy.presentation.quiz.models.QuizUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel  @Inject constructor(
    val quizUseCases: QuizUseCases
): BaseViewModel<QuizUIState, QuizAction>() {

    override fun createInitState(): QuizUIState =
        QuizUIState(
            count = 0,
            question = "",
            answer = listOf(),
            isShowAlert = true,
            isLoading = false,
            isEnd = false,
            isReadyButtonVisible = false,
        )
    lateinit var quiz: List<QuestionWithAnswer>
    private var countOfCurrentQuestion=0
    init {
        getQuestionsWithAnswers()
    }


    fun handleIntent(intent: QuizIntent){
        when(intent){
            is QuizIntent.CreateNewRoom->{
                createRoom()
            }
            is QuizIntent.JoinRoom->{
                joinRoom(intent.roomId)
            }

        }

    }
    suspend fun subscibePlayers(){
            quizUseCases.subPlayers().collect{ combine->
                val players = combine.players
                val readyPlayers = combine.readyPlayers
                _currentState.value = screenState.value.copy(
                    players = "${readyPlayers.size}/${players.size}"
                )
                if(players.size==readyPlayers.size){
                    val q = quiz[countOfCurrentQuestion-1]
                    val currentQuestion =q.question
                    val currentAnswers = q.answers
                    quizUseCases.updateReadinessDown()
                    _currentState.value = screenState.value.copy(
                        question =  currentQuestion.question_text,
                        answer = currentAnswers.map { Pair(it.answer_text, it.is_correct) }
                    )
                }
            }
    }
    fun updateReady(isRight: Boolean){
        countOfCurrentQuestion++
        viewModelScope.launch {
            quizUseCases.updateReadiness()
            if(isRight){
                quizUseCases.updateScore(50)
            }
        }
    }
    fun createRoom(){
        _currentState.value = screenState.value.copy(
            isShowAlert = false,
            isLoading = true
        )
        viewModelScope.launch {
            val roomId =quizUseCases.createRoom()
            _currentState.value = screenState.value.copy(
                roomId = roomId,
                isLoading = false,
                isReadyButtonVisible = true
            )
            subscibePlayers()
        }
    }
    fun joinRoom(roomId: Int){
        viewModelScope.launch {
            try{
                val roomId =  quizUseCases.joinRoom(roomId)
                if(roomId==-1){
                    throw IllegalStateException()
                }
                else{
                    _currentState.value = screenState.value.copy(
                        isShowAlert = false,
                        isLoading = true
                    )
                }
                _currentState.value = screenState.value.copy(
                    roomId = roomId,
                    isLoading = false,
                    isReadyButtonVisible = true
                )
                subscibePlayers()
            }catch (e: Exception){
                Log.d("qi", "error")
                _action.tryEmit(QuizAction.ShowErrorToast)
            }

        }
    }

    fun getQuestionsWithAnswers() {
        viewModelScope.launch {
            quiz =  quizUseCases.getQuestionsWithAnswers()
        }
    }
}