package com.example.quizy.di

import com.example.quizy.data.common.SupabaseClientProvider
import com.example.quizy.data.PhotoRepositoryImpl
import com.example.quizy.data.PlayerRepositoryImpl
import com.example.quizy.domain.GetLeadersUseCase
import com.example.quizy.domain.repositories.PhotoRepository
import com.example.quizy.domain.repositories.PlayerRepository
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
    fun provideSupabase()= SupabaseClientProvider

    @Provides
    @Singleton
    fun providePhotoRepository(
        supabaseClient: SupabaseClientProvider
    ):PhotoRepository = PhotoRepositoryImpl(supabaseClient)

    @Provides
    @Singleton
    fun providePlayersRepository(
        supabaseClient: SupabaseClientProvider
    ):PlayerRepository = PlayerRepositoryImpl(supabaseClient)


    @Provides
    @Singleton
    fun provideLeaderBoardUseCases(
        playerRepository: PlayerRepository,
        photoRepository: PhotoRepository
    ) = GetLeadersUseCase(playerRepository, photoRepository)



}