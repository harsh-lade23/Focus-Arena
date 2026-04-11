package com.example.focusarena.data.mapper

import com.example.focusarena.data.models.ChallengeEntity
import com.example.focusarena.domain.models.Challenge
import com.example.focusarena.domain.models.ChallengeStatus
import com.example.focusarena.domain.models.ChallengeType


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
        challengeStatus = challengeStatus.toString(),
        challengeType = challengeType.toString(),
        createdAt = createdAt
    )
}

private fun stringToChallengeStatus(str: String): ChallengeStatus {

    return when(str){
        "ACTIVE" ->ChallengeStatus.ACTIVE

        "CREATED"->ChallengeStatus.CREATED
        "COMPLETED"->ChallengeStatus.COMPLETED
        else ->ChallengeStatus.CANCELED

    }
}

private fun stringToChallengeType(str: String): ChallengeType{
    return when(str){
        "DUO"-> ChallengeType.DUO
        else -> ChallengeType.GROUP
    }
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
        challengeStatus = stringToChallengeStatus(challengeStatus),
        challengeType = stringToChallengeType(challengeType),
        createdAt = createdAt
    )

}

