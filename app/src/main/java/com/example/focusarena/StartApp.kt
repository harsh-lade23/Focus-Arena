package com.example.focusarena

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.focusarena.navigation.AppNavigation
import com.example.focusarena.navigation.Screen


@Composable
fun App() {
    val appViewModel: AppViewModel = hiltViewModel()
    val isUserLoggedIn = appViewModel.isUserLoggedIn()
    val navController = rememberNavController()

    AppNavigation(
        navController = navController,
        startDestination = if(isUserLoggedIn) Screen.Home.route else Screen.Authentication.route

    )

}