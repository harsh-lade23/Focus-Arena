package com.example.focusarena.data.mapper

import com.example.focusarena.data.models.UserEntity
import com.example.focusarena.domain.models.User


fun UserEntity.toUser(): User {
    return User(
        userId = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl,
        totalPoints = totalPoints,
        streak = streak,
        totalChallengeJoined = totalChallengeJoined,
        totalActiveDays = totalActiveDays,
        totalChallengeWon = totalChallengeWon,
        totalStudiedMinutes = totalStudiedMinutes,
        createdAt = createdAt,
        name = name,
        lastActiveAt = lastActiveAt
    )
}

fun User.toUserEntity(): UserEntity{
    return UserEntity(
        userId = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl,
        totalPoints = totalPoints,
        streak = streak,
        name = name ,
        totalChallengeJoined = totalChallengeJoined,
        totalActiveDays = totalActiveDays,
        totalChallengeWon = totalChallengeWon,
        totalStudiedMinutes = totalStudiedMinutes,
        createdAt = createdAt,
        lastActiveAt = lastActiveAt
    )
}