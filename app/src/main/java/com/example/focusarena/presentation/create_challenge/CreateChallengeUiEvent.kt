package com.example.focusarena.presentation.create_challenge




sealed class CreateChallengeUiEvent {
    data class Create(val data: CreateChallengeUiData): CreateChallengeUiEvent()
    data class Navigate(val route: String): CreateChallengeUiEvent()
    data class ShowSnackBar(val message: String, val actionLabel: String?=null): CreateChallengeUiEvent()
    object ResetUi: CreateChallengeUiEvent()

}