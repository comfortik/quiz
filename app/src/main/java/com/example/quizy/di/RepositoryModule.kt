package com.example.quizy.di

import com.example.quizy.data.common.SharedPreferensesProvider
import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.data.repositoryImpl.AnswerRepositoryImpl
import com.example.quizy.data.repositoryImpl.ClickerPlayerRepositoryImpl
import com.example.quizy.data.repositoryImpl.GameRepositoryImpl
import com.example.quizy.data.repositoryImpl.PhotoRepositoryImpl
import com.example.quizy.data.repositoryImpl.PlayerRepositoryImpl
import com.example.quizy.data.repositoryImpl.QuestionRepositoryImpl
import com.example.quizy.data.repositoryImpl.RoomRepositoryImpl
import com.example.quizy.domain.repositories.AnswerRepository
import com.example.quizy.domain.repositories.ClickerPlayerRepository
import com.example.quizy.domain.repositories.GameRepository
import com.example.quizy.domain.useCases.GetLeadersUseCase
import com.example.quizy.domain.repositories.PhotoRepository
import com.example.quizy.domain.repositories.PlayerRepository
import com.example.quizy.domain.repositories.QuestionRepository
import com.example.quizy.domain.repositories.RoomRepository
import com.example.quizy.domain.useCases.ClickerUseCases
import com.example.quizy.domain.useCases.GameUseCases
import com.example.quizy.domain.useCases.PairsUseCases
import com.example.quizy.domain.useCases.QuizUseCases
import com.example.quizy.presentation.profile.models.ProfileUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSharedPrefs() = SharedPreferensesProvider

    @Provides
    @Singleton
    fun provideSupabase()= SupabaseClientProvider

    @Provides
    @Singleton
    fun providePhotoRepository(
        supabaseClient: SupabaseClientProvider
    ):PhotoRepository = PhotoRepositoryImpl(supabaseClient)

    @Provides
    @Singleton
    fun providePlayersRepository(
        supabaseClient: SupabaseClientProvider,
        sharedPrefs : SharedPreferensesProvider
    ):PlayerRepository = PlayerRepositoryImpl(supabaseClient, sharedPrefs)


    @Provides
    @Singleton
    fun provideLeaderBoardUseCases(
        playerRepository: PlayerRepository,
        photoRepository: PhotoRepository
    ) = GetLeadersUseCase(playerRepository, photoRepository)

    @Provides
    @Singleton
    fun provideClickerUseCases(
        playerRepository: PlayerRepository,
        clickerPlayerRepository: ClickerPlayerRepository,
        sharedPrefs: SharedPreferensesProvider
    ) = ClickerUseCases(playerRepository, clickerPlayerRepository, sharedPrefs)

    @Provides
    @Singleton
    fun provideClickerRepository(
        supabaseClient: SupabaseClientProvider,
        sharedPrefs: SharedPreferensesProvider
    ):ClickerPlayerRepository = ClickerPlayerRepositoryImpl(supabaseClient, sharedPrefs)

    @Provides
    @Singleton
    fun provideGameRepository(
        supabaseClient: SupabaseClientProvider
    ): GameRepository = GameRepositoryImpl(supabaseClient)

    @Provides
    @Singleton
    fun provideGameUseCases(
        gameRepository: GameRepository,
        photoRepository: PhotoRepository
    ): GameUseCases = GameUseCases(gameRepository, photoRepository)

    @Provides
    @Singleton
    fun provideProfileUseCases(
        playerRepository: PlayerRepository,
        photoRepository: PhotoRepository
    ):ProfileUseCases = ProfileUseCases(playerRepository, photoRepository)

    @Provides
    @Singleton
    fun providePairsUseCases(
        photoRepository: PhotoRepository,
        playerRepository: PlayerRepository
    ):PairsUseCases = PairsUseCases(photoRepository, playerRepository)

    @Provides
    @Singleton
    fun provideQuizUseCases(
        roomRepository: RoomRepository,
        playerRepository: PlayerRepository,
        questionRepository: QuestionRepository,
        answerRepository: AnswerRepository
    ): QuizUseCases = QuizUseCases(roomRepository, playerRepository, questionRepository, answerRepository)

    @Provides
    @Singleton
    fun provideQuestionRepository(
        supabaseClient: SupabaseClientProvider
    ):QuestionRepository = QuestionRepositoryImpl(supabaseClient)

    @Provides
    @Singleton
    fun provideAnswersRepository(
        supabaseClient: SupabaseClientProvider
    ):AnswerRepository = AnswerRepositoryImpl(supabaseClient)

    @Provides
    @Singleton
    fun provideRoomRepository(
        supabaseClient: SupabaseClientProvider,
        sharedPrefs: SharedPreferensesProvider
    ): RoomRepository = RoomRepositoryImpl(supabaseClient, sharedPrefs)




}