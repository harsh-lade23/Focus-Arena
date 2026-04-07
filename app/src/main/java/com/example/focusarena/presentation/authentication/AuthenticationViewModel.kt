package com.example.focusarena.presentation.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.models.User
import com.example.focusarena.domain.usecase.authentication.SignInUserUseCase
import com.example.focusarena.domain.usecase.authentication.SignUpUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase,
    private val signUpUserUseCase: SignUpUserUseCase
): ViewModel(){
    private var _authenticationState = MutableStateFlow(AuthenticationScreenState())
    val authenticationScreenState = _authenticationState.asStateFlow()


    fun signInUser(email: String, password: String){
        _authenticationState.value=_authenticationState.value.copy(isLoading = true)

        viewModelScope.launch {
            signInUserUseCase.invoke(email = email, password = password).collect {response->

                when(response){
                    is ResultState.Error -> _authenticationState.value=_authenticationState.value.copy(isLoading = false, error = response.message)
                    is ResultState.Loading -> _authenticationState.value=_authenticationState.value.copy(isLoading = true)
                    is ResultState.Success<*> -> {
                        _authenticationState.value =
                            _authenticationState.value.copy(isLoading = false, success = response.data.toString())
                    }

                }

            }
        }
    }

    fun signUpUser(user: User, password: String){
        _authenticationState.value=_authenticationState.value.copy(isLoading = true)

        viewModelScope.launch {
            signUpUserUseCase.invoke(user, password = password).collect {response->
                Log.d("Firestore Auth", "signUpUser: $response ")
                when(response){
                    is ResultState.Error -> _authenticationState.value=_authenticationState.value.copy(isLoading = false, error = response.message)
                    is ResultState.Loading -> _authenticationState.value=_authenticationState.value.copy(isLoading = true)
                    is ResultState.Success<*> -> {
                        _authenticationState.value =
                            _authenticationState.value.copy(isLoading = false, success = response.data.toString())
                    }

                }

            }
        }
    }
}