package com.example.focusarena.domain.usecase.challenge

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.repository.ParticipantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class JoinChallengeUseCase @Inject constructor(
    private val participantRepository: ParticipantRepository
){
    operator fun invoke(challengeId: String, prize: String): Flow<ResultState<String>> {
        return participantRepository.joinChallenge(challengeId, prize)
    }
}