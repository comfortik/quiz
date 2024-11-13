package com.example.quizy.domain.repositories

import com.example.quizy.presentation.quiz.models.Question

interface QuestionRepository {
    suspend fun fetchQuestions(): List<Question>
}