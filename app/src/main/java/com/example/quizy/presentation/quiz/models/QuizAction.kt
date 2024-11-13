package com.example.quizy.presentation.quiz.models

import com.example.quizy.presentation.common.BaseAction

sealed interface QuizAction: BaseAction {
    data object ShowErrorToast: QuizAction
    data class ShowEndAlert(val name: String, val score: Int): QuizAction
}