package com.example.focusarena.presentation.join_challenge




sealed class EventJoinChallenge{
    data class Navigate(val route: String): EventJoinChallenge()
    data class Join(val challengeId: String, val prize: String): EventJoinChallenge()
}