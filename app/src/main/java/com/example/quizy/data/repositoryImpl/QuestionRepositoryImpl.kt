package com.example.quizy.data.repositoryImpl

import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.repositories.QuestionRepository
import com.example.quizy.presentation.quiz.models.Question
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClientProvider
): QuestionRepository {
    val tableName = "questions"
    override suspend fun fetchQuestions(): List<Question> =
        supabase.client.from(tableName).select().decodeList()
}