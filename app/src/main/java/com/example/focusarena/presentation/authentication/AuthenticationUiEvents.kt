package com.example.focusarena.presentation.authentication





sealed class AuthenticationUiEvents {
    data class Navigate(val route: String): AuthenticationUiEvents()
    data class SignInUser(val email: String, val password: String): AuthenticationUiEvents()
    data class SignUpUser(val email: String, val password: String): AuthenticationUiEvents()
}