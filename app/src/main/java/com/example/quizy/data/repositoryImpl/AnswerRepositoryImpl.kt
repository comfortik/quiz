package com.example.quizy.data.repositoryImpl

import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.domain.repositories.AnswerRepository
import com.example.quizy.presentation.quiz.models.Answer
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class AnswerRepositoryImpl  @Inject constructor(
    private val supabase: SupabaseClientProvider
): AnswerRepository {
    private val tableName = "answers"

    override suspend fun fetchAnswers(): List<Answer> =
        supabase.client.from(tableName).select().decodeList()

}