package com.example.focusarena.core.di

import com.example.focusarena.data.repository.AuthenticationRepositoryImpl
import com.example.focusarena.data.repository.ChallengeRepositoryImpl
import com.example.focusarena.domain.repository.AuthenticationRepository
import com.example.focusarena.domain.repository.ChallengeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthenticationRepository(
        impl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun provideChallengeRepository(
        challengeRepositoryImpl:
        ChallengeRepositoryImpl
    ): ChallengeRepository
}