package com.example.focusarena.data.models

data class ParticipantEntity(
    val participantId: String = "",
    val userId: String = "",
    val challengeId: String = "",
    val owner: Boolean = false,
    val status: String = "",
    val joinedAt: Long = 0,
    val prize: String = "",
    val leftAt: Long? = null,
    val profilePictureUrl: String? = null,
    val totalPoints: Int = 0,
    val totalStudiedMinutes: Int = 0,
    val totalTargetMinutes: Int = 0,
    val totalActiveDays: Int = 0
)
