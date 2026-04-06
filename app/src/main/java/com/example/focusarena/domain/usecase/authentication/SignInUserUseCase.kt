package com.example.focusarena.domain.usecase.authentication

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(email: String, password: String): Flow<ResultState<String>> {
        return authenticationRepository.loginUser(email = email, password)
    }
}