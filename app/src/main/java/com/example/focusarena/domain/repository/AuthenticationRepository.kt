package com.example.focusarena.domain.repository

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun registerUser(user: User, password: String): Flow<ResultState<String>>
    fun loginUser(email: String,  password: String): Flow<ResultState<String>>
}