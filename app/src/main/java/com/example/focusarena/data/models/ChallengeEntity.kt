package com.example.focusarena.data.models

data class ChallengeEntity(
    val challengeId: String,
    val title: String,
    val description: String,
    val ownerId: String,
    val participantLimit: Int,
    val currentParticipantsCount: Int,
    val durationDays: Int,
    val startedAt: Long,
    val endedAt: Long,
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