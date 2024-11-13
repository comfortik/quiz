package com.example.quizy.presentation.quiz.models

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Int,
    val question_id: Int,
    val is_correct: Boolean,
    val answer_text: String
)
