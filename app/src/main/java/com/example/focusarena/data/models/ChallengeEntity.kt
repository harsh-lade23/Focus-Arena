package com.example.focusarena.data.models

data class ChallengeEntity(
    val challengeId: String = "",
    val title: String = "",
    val description: String = "",
    val ownerId: String = "",
    val participantLimit: Int = 0,
    val currentParticipantsCount: Int = 0,
    val durationDays: Int = 0,
    val startedAt: Long? = null,
    val endedAt: Long? = null,
    val challengeStatus: String = "",
    val challengeType: String = "",
    val createdAt: Long = 0
)
