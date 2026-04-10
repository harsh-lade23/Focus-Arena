package com.example.focusarena.navigation





sealed class Screen(val route: String) {
    object Authentication :Screen("authentication")
    object Home: Screen("home")

    object Challenge: Screen("challenge/{challengeId}"){
        fun createRoute(challengeId: String) = "challenge/$challengeId"
    }

    object CreateChallenge: Screen("create_challenge")

    object JoinChallenge: Screen("join_challenge")


}