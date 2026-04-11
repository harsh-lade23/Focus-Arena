package com.example.focusarena.domain.models


data class Challenge(
    val challengeId: String,
    val title: String,
    val description: String,
    val ownerId: String,
    val participantLimit: Int,
    val currentParticipantsCount: Int,
    val durationDays: Int,
    val startedAt: Long?=null,
    val endedAt: Long? = null,
    val challengeStatus: ChallengeStatus,
    val challengeType: ChallengeType,
    val createdAt: Long
)


enum class ChallengeStatus{
    ACTIVE,
    CREATED,
    COMPLETED,
    CANCELED
}
enum class ChallengeType{
    DUO,
    GROUP
}