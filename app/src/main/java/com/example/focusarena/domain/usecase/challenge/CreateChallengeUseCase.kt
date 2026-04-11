package com.example.focusarena.domain.usecase.challenge

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.data.models.ChallengeStatus
import com.example.focusarena.data.models.ChallengeType
import com.example.focusarena.domain.models.Challenge
import com.example.focusarena.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    operator fun invoke(
        challengeName: String,
        participantLimit: Int?,
        durationDays: Int,
        challengeType: ChallengeType,
        description: String = "",

        ): Flow<ResultState<String>> {
        val challenge = Challenge(
            title = challengeName,
            description = description,
            participantLimit = participantLimit?:2,
            currentParticipantsCount = 1,
            durationDays = durationDays,
            challengeStatus = ChallengeStatus.CREATED,
            challengeType = challengeType,
            createdAt = System.currentTimeMillis(),
            challengeId = "",
            ownerId = "",
        )
        return challengeRepository.createChallenge(challenge)
    }
}