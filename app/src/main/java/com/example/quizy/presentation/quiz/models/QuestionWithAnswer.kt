package com.example.quizy.presentation.quiz.models

data class QuestionWithAnswer(
    val question: Question,
    val answers: List<Answer>
)