package com.example.focusarena.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.focusarena.AppViewModel
import com.example.focusarena.presentation.authentication.AuthenticationScreen


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
        }
        composable(Screen.Authentication.route) {
            AuthenticationScreen()
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