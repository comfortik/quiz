package com.example.quizy.presentation.quiz.models

sealed interface QuizIntent{
    data object CreateNewRoom: QuizIntent
    data class JoinRoom(val roomId: Int): QuizIntent
    data class UpdateReady(val isRight: Boolean = false): QuizIntent
    data object LeaveGame: QuizIntent
}