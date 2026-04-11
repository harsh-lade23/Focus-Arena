package com.example.focusarena.presentation.create_challenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.focusarena.data.models.ChallengeType


@Composable
fun CreateChallengeScreen(
    viewModel: CreateChallengeViewModel,
    navController: NavController
){
    val createChallengeUiState = viewModel.createChallengeUiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    val name = remember { mutableStateOf("") }
    val duration = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf(ChallengeType.DUO) }
    val participantLimit = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent){
                is CreateChallengeUiEvent.Navigate ->{
                    navController.navigate(uiEvent.route)
                }
                is CreateChallengeUiEvent.ResetUi -> {
                    name.value =""
                    duration.value =""
                    selectedType.value = ChallengeType.DUO
                    participantLimit.value =""

                }
                is CreateChallengeUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(uiEvent.message, actionLabel = uiEvent.actionLabel)
                }
                else ->{}

            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { innerPadding ->

        CreateChallengeUi(
            Modifier.padding(innerPadding),
            name,
            duration,
            selectedType,
            participantLimit,
            createChallengeUiState
        ) {
            viewModel.onEvent(CreateChallengeUiEvent.Create(it))
        }
    }
}

@Composable
private fun CreateChallengeUi(
    modifier: Modifier,
    name: MutableState<String>,
    duration: MutableState<String>,
    selectedType: MutableState<ChallengeType>,
    participantLimit: MutableState<String>,
    createChallengeUiState: State<CreateChallengeUiState>,
    onCreateClick: (CreateChallengeUiData) -> Unit
) {


    Column(
        modifier = modifier
            .fillMaxSize()

            .padding(16.dp)
    )
    {

        Text(
            text = "Create Challenge",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Challenge Name
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Challenge Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Challenge Type
        Text("Challenge Type")

        Row {
            listOf(ChallengeType.DUO, ChallengeType.GROUP).forEach { type ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedType.value == type,
                        onClick = { selectedType.value = type }
                    )
                    Text(type.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Duration
        OutlinedTextField(
            value = duration.value,
            onValueChange = { duration.value = it },
            label = { Text("Duration (days)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Participant Limit (only for GROUP)
        if (selectedType.value == ChallengeType.GROUP) {
            OutlinedTextField(
                value = participantLimit.value,
                onValueChange = { participantLimit.value = it },
                label = { Text("Participant Limit") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if(!createChallengeUiState.value.isLoading) {
                    val data = CreateChallengeUiData(
                        name = name.value,
                        type = selectedType.value,
                        durationDays = duration.value.toIntOrNull() ?: 0,
                        participantLimit = if (selectedType.value == ChallengeType.GROUP) {
                            participantLimit.value.toIntOrNull()
                        } else null
                    )
                    onCreateClick(data)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if(createChallengeUiState.value.isLoading){
                CircularProgressIndicator()
            }
            else {
                Text("Create Challenge")
            }
        }
        if (createChallengeUiState.value.error!=null){
            Text(createChallengeUiState.value.error!!)
        }
    }
}