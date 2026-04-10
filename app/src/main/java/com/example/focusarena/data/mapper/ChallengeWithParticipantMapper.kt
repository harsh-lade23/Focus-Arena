package com.example.focusarena.data.mapper

import com.example.focusarena.data.models.ChallengeWithParticipantEntity
import com.example.focusarena.data.models.ChallengeWithParticipants
import com.example.focusarena.domain.models.ChallengeWithParticipant


fun ChallengeWithParticipant.toChallengeWithParticipantEntity(): ChallengeWithParticipantEntity{
    return ChallengeWithParticipantEntity(challengeEntity = challenge.toEntity(), participant.toEntity())
}

fun ChallengeWithParticipantEntity.toChallengeWithParticipant(): ChallengeWithParticipant{
    return ChallengeWithParticipant(challengeEntity.toDomain(), participant = participantEntity.toDomain())
}