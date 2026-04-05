package com.example.focusarena.data.models

data class ChallengeDailySummaryEntity(
    val userId: String,
    val challengeId: String,
    val date: Long,
    val totalPoints: Int,
    val totalStudiedMinutes: Int,
    val totalTargetMinutes: Int,
    val totalSessionCompleted: Int
)