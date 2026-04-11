package com.example.focusarena.domain.repository

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.data.models.ChallengeWithParticipants
import com.example.focusarena.domain.models.Challenge
import com.example.focusarena.domain.models.ChallengeWithParticipant
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getChallenges() : Flow<ResultState<List<ChallengeWithParticipant>>>
    fun getChallengeById(challengeId: String) : Flow<ResultState<ChallengeWithParticipants>>
    fun createChallenge(challenge: Challenge, winningPrize: String) : Flow<ResultState<String>>
    fun startChallenge(challengeId: String) : Flow<ResultState<String>>

    fun joinChallenge(challengeId: String, participantPrize: String): Flow<ResultState<String>>
}