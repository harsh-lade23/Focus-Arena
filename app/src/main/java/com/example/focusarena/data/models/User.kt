package com.example.focusarena.data.models

data class UserEntity(
    val userId: String,
    val username: String,
    val email: String,
    val profilePictureUrl: String? = null,

    val totalPoints: Int = 0,
    val streak: Int = 0,
    val totalChallengeJoined: Int = 0,
    val totalActiveDays: Int = 0,
    val totalChallengeWon: Int = 0,
    val totalStudiedMinutes: Int = 0,
    val createdAt: Long,
    val lastActiveAt: Long

)