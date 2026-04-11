package com.example.focusarena.domain.models

import com.example.focusarena.data.models.ParticipantStatus


data class Participant(
    val participantId: String,
    val userId: String,
    val challengeId: String,
    val isOwner: Boolean,
    val status: ParticipantStatus,
    val joinedAt: Long,
    val prize: String,
    val leftAt: Long? = null,
    val profilePictureUrl: String? = null,
    val totalPoints: Int = 0,

    val totalStudiedMinutes: Int = 0,
    val totalTargetMinutes: Int = 0,
    val totalActiveDays: Int = 0,

    )

