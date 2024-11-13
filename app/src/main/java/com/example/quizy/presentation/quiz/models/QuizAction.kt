package com.example.quizy.presentation.quiz.models

import com.example.quizy.presentation.common.BaseAction

sealed interface QuizAction: BaseAction {
    data object ShowErrorToast: QuizAction
}