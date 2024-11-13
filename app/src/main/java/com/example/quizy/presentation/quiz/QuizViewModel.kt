package com.example.quizy.presentation.quiz

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.quizy.domain.useCases.QuizUseCases
import com.example.quizy.presentation.common.BaseViewModel
import com.example.quizy.presentation.quiz.models.QuestionWithAnswer
import com.example.quizy.presentation.quiz.models.QuizAction
import com.example.quizy.presentation.quiz.models.QuizIntent
import com.example.quizy.presentation.quiz.models.QuizUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel  @Inject constructor(
    val quizUseCases: QuizUseCases
): BaseViewModel<QuizUIState, QuizAction>() {
    private var isDialogShowed = false
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
    private var isAdmin = false
    init {
        getQuestionsWithAnswers()
    }

    fun handleIntent(intent: QuizIntent){
        when(intent){
            is QuizIntent.CreateNewRoom->{
                createRoom()
                isAdmin=true
            }
            is QuizIntent.JoinRoom->{
                joinRoom(intent.roomId)

            }

            is QuizIntent.UpdateReady->
                updateReady(intent.isRight)

            is QuizIntent.LeaveGame->{
                if(isAdmin){
                    viewModelScope.launch {
                        quizUseCases.endGame(emptyList())
                    }
                }
            }

        }

    }


    private fun subscribeRoom(){
        viewModelScope.launch {
            quizUseCases.subRoom().collect{
                if (it.isEnded!!){
                    if(!isDialogShowed){
                        isDialogShowed = true
                        _action.emit(QuizAction.ShowEndAlert(it.leader?.name?:"Админ ливнул", it.leader?.score?:0))

                    }
                }
                if(it.isStarted){
                    _currentState.value = screenState.value.copy(
                        isReadyButtonVisible = false
                    )
                }

                val players = it.playerIds
                val readyPlayers = it.readyPlayerIds
                _currentState.value = screenState.value.copy(
                    players = "${readyPlayers?.size}/${players.size}"
                )


                if(
                    it.readyPlayerIds?.toSet()== it.playerIds.toSet()
                    ){

                    if(isAdmin) {
                        if(!it.isStarted){
                            quizUseCases.startGame()
                        }
                        quizUseCases.updateReadinessDown()
                        if(it.currentQuestion<quiz.size-1){
                            quizUseCases.nextQuestion(it.currentQuestion + 1)
                        }else{
                            quizUseCases.endGame(it.playerIds)
                        }

                    }
                }

                if(it.currentQuestion>0){
                    updateQuestions(it.currentQuestion)
                }
            }


        }
    }

    private fun updateQuestions(num: Int){
        val q = quiz[num]
        val currentQuestion =q.question
        val currentAnswers = q.answers
        _currentState.value = screenState.value.copy(
            question =  currentQuestion.question_text,
            answer = currentAnswers.map { Pair(it.answer_text, it.is_correct) }
        )
    }

    private fun updateReady(isRight: Boolean){
        viewModelScope.launch {
            Log.d("view,odel", "$isRight")
            if(isRight){
                quizUseCases.updateScore(50)
            }
            quizUseCases.updateReadiness()

        }
    }
    private fun createRoom(){
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
            subscribeRoom()
        }
    }
    private fun joinRoom(roomId: Int){
        Log.d("viewmodel", "id $roomId")
        viewModelScope.launch {
            try{
                val id =  quizUseCases.joinRoom(roomId)
                if(id==-1){
                    throw IllegalStateException()
                }
                else{
                    _currentState.value = screenState.value.copy(
                        isShowAlert = false,
                        isLoading = true
                    )
                    _currentState.value = screenState.value.copy(
                        roomId = id,
                        isLoading = false,
                        isReadyButtonVisible = true
                    )
                    subscribeRoom()
                }

            }catch (e: Exception){
                _action.tryEmit(QuizAction.ShowErrorToast)
            }

        }
    }

    private fun getQuestionsWithAnswers() {
        viewModelScope.launch {
            quiz =  quizUseCases.getQuestionsWithAnswers().take(3)
        }
    }
}