package com.example.focusarena.presentation.home

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
}