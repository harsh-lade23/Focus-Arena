package com.example.focusarena.domain.repository

import com.example.focusarena.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface ParticipantRepository{
    fun joinChallenge(challengeId: String, participantPrize: String): Flow<ResultState<String>>

}