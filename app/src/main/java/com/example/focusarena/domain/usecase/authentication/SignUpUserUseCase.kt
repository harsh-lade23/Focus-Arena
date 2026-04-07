package com.example.focusarena.domain.usecase.authentication

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.models.User
import com.example.focusarena.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(user: User, password: String): Flow<ResultState<String>> {
        return authenticationRepository.registerUser(user, password = password)
    }
}