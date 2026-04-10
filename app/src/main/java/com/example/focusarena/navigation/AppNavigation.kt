package com.example.focusarena.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.focusarena.presentation.home.HomeScreen
import com.example.focusarena.presentation.authentication.AuthenticationScreen
import com.example.focusarena.presentation.authentication.AuthenticationViewModel


@Composable
fun AppNavigation (
    navController: NavHostController,
    startDestination: String
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            //TODO - CALL ALL CHALLENGE SCREEN/ HOME
            HomeScreen(navController = navController)
        }
        composable(Screen.Authentication.route) {
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            AuthenticationScreen(
                authenticationViewModel = authenticationViewModel,
                navController = navController
            )
        }
        composable(
            route = Screen.Challenge.route,
            arguments = listOf(
                navArgument("challengeId") {
                    type = NavType.StringType
                }
            )
        ) { backstack ->
            val challengeId= backstack.arguments?.getString("challengeId")!!
            //TODO - call challenge screen

        }

    }
}