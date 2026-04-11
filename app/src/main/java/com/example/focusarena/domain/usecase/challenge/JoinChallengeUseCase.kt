package com.example.focusarena.domain.usecase.challenge

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class JoinChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
){
    operator fun invoke(challengeId: String, prize: String): Flow<ResultState<String>> {
        return challengeRepository.joinChallenge(challengeId, prize)
    }
}