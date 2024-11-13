package com.example.quizy.domain.repositories

import com.example.quizy.presentation.quiz.models.Answer

interface AnswerRepository {
    suspend fun fetchAnswers(): List<Answer>
}