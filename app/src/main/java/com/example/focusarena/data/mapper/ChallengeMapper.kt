package com.example.focusarena.data.mapper

import com.example.focusarena.data.models.ChallengeEntity
import com.example.focusarena.domain.models.Challenge


fun Challenge.toEntity(): ChallengeEntity {
    return ChallengeEntity(
        challengeId = challengeId,
        title = title,
        description = description,
        ownerId = ownerId,
        participantLimit = participantLimit,
        currentParticipantsCount = currentParticipantsCount,
        durationDays = durationDays,
        startedAt = startedAt,
        endedAt = endedAt,
        challengeStatus = challengeStatus,
        challengeType = challengeType,
        createdAt = createdAt
    )
}

fun ChallengeEntity.toDomain(): Challenge{
    return Challenge(
        challengeId = challengeId,
        title = title,
        description = description,
        ownerId = ownerId,
        participantLimit = participantLimit,
        currentParticipantsCount = currentParticipantsCount,
        durationDays = durationDays,
        startedAt = startedAt,
        endedAt = endedAt,
        challengeStatus = challengeStatus,
        challengeType = challengeType,
        createdAt = createdAt
    )

}

