package com.example.focusarena.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.focusarena.domain.models.User
import com.example.focusarena.navigation.Screen


@Composable
fun AuthenticationScreen(
    authenticationViewModel: AuthenticationViewModel,
    navController: NavController
) {

    LaunchedEffect(Unit) {
        authenticationViewModel.uiEvents.collect { uiEvent ->
            when(uiEvent){
                is AuthenticationUiEvents.Navigate -> {
                    navController.navigate(uiEvent.route){
                        popUpTo(Screen.Authentication.route){
                            inclusive=true
                        }
                    }

                }
                else -> {
                }
            }
        }
    }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val name = remember { mutableStateOf("") }

    val context = LocalContext.current
    val screenState =
        authenticationViewModel.authenticationScreenState.collectAsStateWithLifecycle()


    val currentScreen = remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (screenState.value.error != null) {
            Toast.makeText(context, screenState.value.error!!, Toast.LENGTH_SHORT).show()
            screenState.value.error = null
        }
        if (screenState.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (screenState.value.success != null) {
            //TODO - Show a welcome or success card
            Text(text = screenState.value.success!!, modifier = Modifier.align(Alignment.Center))
        } else {
            if (currentScreen.value == 0) {



                SignInCard(
                    authenticationViewModel = authenticationViewModel,
                    currentScreen = currentScreen,
                    email,
                    password
                )
            } else {
                SingUpCard(
                    authenticationViewModel, currentScreen, email, password, name
                )
            }
        }

    }


}


@Composable
fun SignInCard(
    authenticationViewModel: AuthenticationViewModel,
    currentScreen: MutableState<Int>,
    email: MutableState<String>,
    password: MutableState<String>
) {

    Card(modifier = Modifier.padding(vertical = 100.dp, horizontal = 20.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Sign In",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.padding(20.dp))
            TextField(value = email.value, onValueChange = {
                email.value = it
            }, label = {
                Text("Email")
            })
            Spacer(modifier = Modifier.padding(20.dp))

            TextField(value = password.value, onValueChange = {
                password.value = it
            }, label = {
                Text("Password")
            })
            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = {
                    authenticationViewModel.signInUser(email.value, password.value)
                }) {
                Text("Sign In")
            }
            Spacer(modifier = Modifier.padding(20.dp))


            Text(
                "Don't have an account? Sign Up",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        currentScreen.value = 1
                    },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}


@Composable
fun SingUpCard(
    authenticationViewModel: AuthenticationViewModel,
    currentScreen: MutableState<Int>,
    email: MutableState<String>,
    password: MutableState<String>,
    name: MutableState<String>
) {


    Card(modifier = Modifier.padding(vertical = 100.dp, horizontal = 20.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                "Sign Up",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.padding(20.dp))
            TextField(value = name.value, onValueChange = {
                name.value = it
            }, label = {
                Text("Name")
            })
            Spacer(modifier = Modifier.padding(20.dp))
            TextField(value = email.value, onValueChange = {
                email.value = it
            }, label = {
                Text("Email")
            })
            Spacer(modifier = Modifier.padding(20.dp))

            TextField(value = password.value, onValueChange = {
                password.value = it
            }, label = {
                Text("Password")
            })
            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = {
                    authenticationViewModel.signUpUser(
                        user = User(
                            email = email.value,
                            name = name.value,
                            username = name.value,
                            createdAt = System.currentTimeMillis(),
                            lastActiveAt = System.currentTimeMillis(),
                            userId = "",

                            ), password.value
                    )
                }) {
                Text("Sign Up")
            }
            Spacer(modifier = Modifier.padding(20.dp))


            Text(
                "Already have an account? Sign In",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        currentScreen.value = 0
                    },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }


}