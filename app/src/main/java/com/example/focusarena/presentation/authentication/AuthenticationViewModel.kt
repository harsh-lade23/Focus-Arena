package com.example.focusarena.presentation.authentication

import com.example.focusarena.domain.usecase.authentication.SignInUserUseCase
import com.example.focusarena.domain.usecase.authentication.SignUpUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase,
    private val signUpUserUseCase: SignUpUserUseCase
){
}