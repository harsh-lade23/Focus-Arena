package com.example.focusarena.data.models

data class StudySessionEntity(
    val sessionId: String,
    val userId: String,
    val title: String,
    val startedAt: Long,
    val updatedAt: Long,
    val targetMinutes: Int,
    val studiedMinutes:Int,
    val isCompleted: Boolean,
    val endedAt: Long? = null,
    val createdAt: Long

)
