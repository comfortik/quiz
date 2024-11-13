package com.example.quizy.presentation.quiz.models

import kotlinx.serialization.Serializable


@Serializable
data class Question(
    val id: Int,
    val question_text: String
)