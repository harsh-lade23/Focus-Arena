package com.example.focusarena.presentation.create_challenge

import com.example.focusarena.data.models.ChallengeType


data class CreateChallengeUiState(
    var isLoading: Boolean =false,
    var error: String? = null
)


data class CreateChallengeUiData(
    val name: String,
    val type: ChallengeType,
    val durationDays: Int,
    val participantLimit: Int?
)
