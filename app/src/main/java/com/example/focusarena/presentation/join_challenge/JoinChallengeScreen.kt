package com.example.focusarena.presentation.join_challenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController


@Composable
fun JoinChallengeScreen(
    joinChallengeViewModel: JoinChallengeViewModel,
    navController: NavHostController
) {
    val uiState = joinChallengeViewModel.uiState.collectAsStateWithLifecycle()

    val challengeId = remember{mutableStateOf("")}
    val prize = remember{mutableStateOf("")}

    LaunchedEffect(true) {
        joinChallengeViewModel.uiEvent.collect { uiEvent->
            when(uiEvent){
                is EventJoinChallenge.Navigate ->{
                    navController.popBackStack()
                    navController.navigate(uiEvent.route)
                }
                else->{}
            }
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text("Join Challenge")
            if(uiState.value.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            else if(uiState.value.error!=null){
                Text(uiState.value.error!!)
            }
            else{
                Spacer(modifier = Modifier.padding(16.dp))

                TextField(
                    value = challengeId.value,
                    onValueChange = {
                        challengeId.value =it
                    },
                    placeholder = {
                        Text("Enter Challenge Id")
                    }

                )

                Spacer(modifier = Modifier.padding(16.dp))

                TextField(
                    value = prize.value,
                    onValueChange = {
                        prize.value =it
                    },
                    placeholder = {
                        Text("Prize if I win")
                    }

                )
                Spacer(modifier =Modifier.padding(16.dp))
                Button(
                    onClick = {
                        joinChallengeViewModel.onEvent(EventJoinChallenge.Join(challengeId = challengeId.value, prize.value))
                    }
                ) {
                    Text("Join")

                }

            }

        }


    }


}