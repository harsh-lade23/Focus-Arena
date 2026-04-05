package com.example.focusarena.data.models

data class ParticipantEntity(
    val participantId: String,
    val userId: String,
    val challengeId: String,
    val isOwner: Boolean,
    val status: ParticipantStatus,
    val joinedAt: Long,
    val leftAt: Long? = null,
    val profilePictureUrl: String? = null,
    val totalPoints: Int = 0,

    val totalStudiedMinutes: Int = 0,
    val totalTargetMinutes: Int = 0,
    val totalActiveDays: Int = 0,


)

enum class ParticipantStatus{
    ACTIVE,
    INACTIVE,
    LEFT,
    BANNED
}