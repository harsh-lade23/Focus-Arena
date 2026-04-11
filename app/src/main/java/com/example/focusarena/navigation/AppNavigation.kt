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
import com.example.focusarena.presentation.challenge.ChallengeScreen
import com.example.focusarena.presentation.create_challenge.CreateChallengeScreen
import com.example.focusarena.presentation.home.HomeViewModel
import com.example.focusarena.presentation.join_challenge.JoinChallengeScreen


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
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(navController = navController, homeViewModel)
        }
        composable(Screen.Authentication.route) {
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            AuthenticationScreen(
                authenticationViewModel = authenticationViewModel,
                navController = navController
            )
        }
        composable(Screen.CreateChallenge.route) {
            CreateChallengeScreen()
        }
        composable(Screen.JoinChallenge.route){
            JoinChallengeScreen()
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

            ChallengeScreen(challengeId)

        }

    }
}