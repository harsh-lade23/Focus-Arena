package com.example.focusarena.domain.usecase.challenge

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.models.Challenge
import com.example.focusarena.domain.models.ChallengeWithParticipant
import com.example.focusarena.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllChallengesUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    operator fun invoke(): Flow<ResultState<List<ChallengeWithParticipant>>> {
        return challengeRepository.getChallenges()
    }
}