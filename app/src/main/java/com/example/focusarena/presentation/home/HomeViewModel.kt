package com.example.focusarena.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.domain.usecase.challenge.GetAllChallengesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllChallengesUseCase: GetAllChallengesUseCase
) : ViewModel(){
    private var _homeUiState = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val homeUiState = _homeUiState.asStateFlow()

    private var _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        getAllChallenges()
    }

    fun onEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    _uiEvents.send(uiEvent)
                }
            }
        }
    }

    fun getAllChallenges(){
        _homeUiState.value =  _homeUiState.value.copy(isLoading = true)

        viewModelScope.launch {
            //todo - load user name, when usecase created.
            getAllChallengesUseCase.invoke().collect {  result->
                when(result){
                    is ResultState.Loading -> _homeUiState.value =  _homeUiState.value.copy(isLoading = true)
                    is ResultState.Error -> _homeUiState.value = _homeUiState.value.copy(isLoading = false, error = result.message)
                    is ResultState.Success-> {

                        _homeUiState.value =
                            _homeUiState.value.copy(isLoading = false,  challengeWithParticipant =  result.data)
                    }
                }

            }
        }

    }
}