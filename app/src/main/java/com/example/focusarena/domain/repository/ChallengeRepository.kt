package com.example.focusarena.domain.repository

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.data.models.ChallengeWithParticipants
import com.example.focusarena.domain.models.Challenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getChallenges() : Flow<ResultState<List<Challenge>>>
    fun getChallengeById(challengeId: String) : Flow<ResultState<ChallengeWithParticipants>>
    fun createChallenge(challenge: Challenge) : Flow<ResultState<String>>
    fun startChallenge(challengeId: String) : Flow<ResultState<String>>
}