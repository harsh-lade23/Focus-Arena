package com.example.focusarena.data.models

data class SessionParticipationEntity(
    val sessionParticipationId: String,
    val userId: String,
    val sessionId: String,
    val challengeId: String,
    val participantId: String,
    val studiedMinutes: Int,
    val createdAt: Long
)
