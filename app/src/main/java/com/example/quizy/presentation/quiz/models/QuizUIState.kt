package com.example.quizy.presentation.quiz.models

import com.example.quizy.presentation.common.BaseState

data class QuizUIState(
    val count: Int =0,
    val roomId:Int=0,
    val question: String = "",
    val players: String = "",
    val answer: List<Pair<String, Boolean>> = listOf(),
    val isShowAlert: Boolean = true,
    val isLoading: Boolean = false,
    val isEnd: Boolean = false  ,
    val isReadyButtonVisible: Boolean = false
): BaseState