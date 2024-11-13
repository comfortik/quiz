package com.example.quizy.domain.useCases

import com.example.quizy.domain.repositories.AnswerRepository
import com.example.quizy.domain.repositories.PlayerRepository
import com.example.quizy.domain.repositories.QuestionRepository
import com.example.quizy.domain.repositories.RoomRepository
import com.example.quizy.presentation.quiz.models.PlayersReadyPlayers
import com.example.quizy.presentation.quiz.models.QuestionWithAnswer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class QuizUseCases @Inject constructor(
    val roomRepository: RoomRepository,
    val playerRepository: PlayerRepository,
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository
) {
    suspend fun createRoom() = roomRepository.createRoom()
    suspend fun joinRoom(roomId: Int) = roomRepository.joinRoom(roomId)
    suspend fun subPlayers(): Flow<PlayersReadyPlayers> {
        val players = roomRepository.getPlayers()
        val readyPlayers = roomRepository.getReadyPlayers()
        return combine(players, readyPlayers) { players, readyPlayers ->
            PlayersReadyPlayers(players, readyPlayers)
        }
    }
    suspend fun updateReadiness(){
        roomRepository.updateReadyPlayers()
    }
    suspend fun updateScore(score: Int){
        playerRepository.updateTotalScore(score)
    }
    suspend fun updateReadinessDown(){
        roomRepository.updateReadyPlayersDown()
    }
    suspend fun getQuestionsWithAnswers(): List<QuestionWithAnswer>{
        val qustions = questionRepository.fetchQuestions()
        val answers = answerRepository.fetchAnswers()
        val questionWithAnswer =
            qustions.map{ question->
                QuestionWithAnswer(
                    question = question,
                    answers = answers.filter { it.question_id== question.id }
                )
            }
        return questionWithAnswer
    }



}