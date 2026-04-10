package com.example.focusarena.data.mapper

import com.example.focusarena.data.models.ParticipantEntity
import com.example.focusarena.domain.models.Participant


fun Participant.toEntity(): ParticipantEntity {
    return ParticipantEntity(
        participantId = participantId,
        userId = userId,
        challengeId = challengeId,
        isOwner = isOwner,
        status = status,
        joinedAt = joinedAt,
        leftAt = leftAt,
        profilePictureUrl = profilePictureUrl,
        totalPoints = totalPoints,
        totalStudiedMinutes = totalStudiedMinutes,
        totalTargetMinutes = totalTargetMinutes,
        totalActiveDays = totalActiveDays


    )
}
fun ParticipantEntity.toDomain(): Participant{
    return Participant(
        participantId = participantId,
        userId = userId,
        challengeId = challengeId,
        isOwner = isOwner,
        status = status,
        joinedAt = joinedAt,
        leftAt = leftAt,
        profilePictureUrl = profilePictureUrl,
        totalPoints = totalPoints,
        totalStudiedMinutes = totalStudiedMinutes,
        totalTargetMinutes = totalTargetMinutes,
        totalActiveDays = totalActiveDays
    )
}