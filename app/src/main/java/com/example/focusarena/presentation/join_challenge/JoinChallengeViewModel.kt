package com.example.focusarena.presentation.join_challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.usecase.challenge.JoinChallengeUseCase
import com.example.focusarena.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JoinChallengeViewModel @Inject constructor(
    private val joinChallengeUseCase: JoinChallengeUseCase
) : ViewModel() {

    private var _uiEvent = Channel<EventJoinChallenge>()
    val uiEvent  = _uiEvent.receiveAsFlow()

    private var _uiState = MutableStateFlow(StateJoinChallenge())
    val uiState = _uiState.asStateFlow()


    fun onEvent(eventJoinChallenge: EventJoinChallenge){
        viewModelScope.launch {
            when (eventJoinChallenge) {
                is EventJoinChallenge.Join -> {
                    joinChallenge(eventJoinChallenge.challengeId, eventJoinChallenge.prize)
                }

                is EventJoinChallenge.Navigate -> {
                    _uiEvent.send(eventJoinChallenge)
                }
            }
        }
    }

    fun joinChallenge(challengeId: String, prize: String){

        viewModelScope.launch {
            joinChallengeUseCase.invoke(challengeId = challengeId, prize).collect{resultState ->
                when(resultState){
                    is ResultState.Error ->{
                        _uiState.value = _uiState.value.copy(isLoading = false, error =  resultState.message)
                    }
                    is ResultState.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onEvent(EventJoinChallenge.Navigate(Screen.Challenge.createRoute(challengeId = challengeId)))
                    }
                }
            }
        }

    }



}