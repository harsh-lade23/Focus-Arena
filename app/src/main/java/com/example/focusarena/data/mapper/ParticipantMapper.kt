package com.example.focusarena.data.mapper

import com.example.focusarena.data.models.ParticipantEntity
import com.example.focusarena.domain.models.Participant
import com.example.focusarena.domain.models.ParticipantStatus


fun Participant.toEntity(): ParticipantEntity {
    return ParticipantEntity(
        participantId = participantId,
        userId = userId,
        challengeId = challengeId,
        owner = isOwner,
        status = status.toString(),
        joinedAt = joinedAt,
        leftAt = leftAt,
        profilePictureUrl = profilePictureUrl,
        totalPoints = totalPoints,
        totalStudiedMinutes = totalStudiedMinutes,
        totalTargetMinutes = totalTargetMinutes,
        totalActiveDays = totalActiveDays,
        prize = prize


    )
}

private fun stringToParticipant(str: String): ParticipantStatus{

    return when (str) {
        "ACTIVE" -> ParticipantStatus.ACTIVE
        "BANNED" -> ParticipantStatus.BANNED
        "INACTIVE" -> ParticipantStatus.INACTIVE
        else -> ParticipantStatus.LEFT
    }
}
fun ParticipantEntity.toDomain(): Participant{
    return Participant(
        participantId = participantId,
        userId = userId,
        challengeId = challengeId,
        isOwner = owner,
        status = stringToParticipant(str = status),
        joinedAt = joinedAt,
        leftAt = leftAt,
        profilePictureUrl = profilePictureUrl,
        totalPoints = totalPoints,
        totalStudiedMinutes = totalStudiedMinutes,
        totalTargetMinutes = totalTargetMinutes,
        totalActiveDays = totalActiveDays,
        prize = prize

    )
}