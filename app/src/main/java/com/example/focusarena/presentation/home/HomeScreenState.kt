package com.example.focusarena.presentation.home

import com.example.focusarena.domain.models.Challenge
import com.example.focusarena.navigation.Screen


data class HomeScreenState(
    val userName: String?= null,
    val challengeList: List<Challenge>? = null,
    val isLoading: Boolean = false,
    val error: String? =null
)