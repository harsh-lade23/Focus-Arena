package com.example.focusarena.presentation.authentication





data class AuthenticationScreenState(
    val isLoading: Boolean =false,
    var error: String?=null,
    val success: String? = null
)