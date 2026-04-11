package com.example.focusarena.presentation.create_challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.usecase.challenge.CreateChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val createChallengeUseCase: CreateChallengeUseCase
): ViewModel() {

    private var _createChallengeUiState = MutableStateFlow(CreateChallengeUiState())
    val createChallengeUiState = _createChallengeUiState.asStateFlow()

    private var _uiEvent = Channel<CreateChallengeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(uiEvent: CreateChallengeUiEvent){
        viewModelScope.launch {
            when (uiEvent) {
                is CreateChallengeUiEvent.Create -> {
                    createChallenge(uiEvent.data)
                }

                is CreateChallengeUiEvent.Navigate -> {
                    _uiEvent.send(uiEvent)
                }

                is CreateChallengeUiEvent.ResetUi -> {
                    _uiEvent.send(uiEvent)
                }
                is CreateChallengeUiEvent.ShowSnackBar -> {
                    _uiEvent.send(uiEvent)
                }
            }
        }
    }

    fun createChallenge(createChallengeUiData: CreateChallengeUiData){
        viewModelScope.launch {
            createChallengeUseCase.invoke(
                challengeName = createChallengeUiData.name,
                participantLimit = createChallengeUiData.participantLimit,
                durationDays = createChallengeUiData.durationDays,
                challengeType = createChallengeUiData.type,
            ).collect {value ->
                when(value){
                    is ResultState.Success ->{
                        _createChallengeUiState.value = _createChallengeUiState.value.copy(isLoading = false, error = null)
                        onEvent(CreateChallengeUiEvent.ResetUi)
                        onEvent(CreateChallengeUiEvent.ShowSnackBar("Challenge Created Successfully", ))
                    }
                    is ResultState.Loading -> {
                        _createChallengeUiState.value = _createChallengeUiState.value.copy(isLoading = true)

                    }
                    is ResultState.Error -> {
                        _createChallengeUiState.value = _createChallengeUiState.value.copy(isLoading = false, error = "Failed to create Challenge, please try again")
                        onEvent(CreateChallengeUiEvent.ShowSnackBar(value.message, actionLabel = "Retry"))

                    }
                }
            }
        }
    }

}




