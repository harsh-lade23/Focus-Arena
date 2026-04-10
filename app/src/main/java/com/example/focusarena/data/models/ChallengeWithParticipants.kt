package com.example.focusarena.data.models

import com.example.focusarena.domain.models.Challenge
import com.example.focusarena.domain.models.Participant

data class ChallengeWithParticipants(
    val challenge: Challenge,
    val participants: List<Participant>
)