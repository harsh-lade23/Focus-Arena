package com.example.focusarena.data.models

data class UserDailySummaryEntity(
    val userId: String,
    val totalPoints: Int,
    val totalStudiedMinutes: Int,
    val totalTargetMinutes: Int,
    val totalSessionCreated: Int = 0,
    val totalSessionCompleted: Int = 0,
    val date: Long,
)