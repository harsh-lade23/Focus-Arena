package com.example.focusarena.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.focusarena.core.utils.DateConversionFunctions
import com.example.focusarena.data.models.ChallengeStatus
import com.example.focusarena.navigation.Screen


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { uiEvent ->
            when (uiEvent){
                is UiEvent.Navigate -> {
                    navController.navigate(uiEvent.route)
                }
            }
        }
    }
    val homeScreenState = viewModel.homeUiState.collectAsStateWithLifecycle()
    val challengeWithParticipant = homeScreenState.value.challengeWithParticipant
    Surface(
        modifier = Modifier.fillMaxSize()
    ){
        if(homeScreenState.value.isLoading){
            Box(modifier =Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else if(homeScreenState.value.error!=null){
            Box(modifier =Modifier.fillMaxSize()) {
                Text(
                    homeScreenState.value.error!!,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HomeHeader(
                    viewModel = viewModel,
                    userName = homeScreenState.value.userName
                        ?: "" //todo - change according to viewmodel
                )


                if(homeScreenState.value.challengeWithParticipant==null){
                    Text(
                        "Failed to load Challenges",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        "Click Here to refresh",
                        modifier = Modifier.align(Alignment.CenterHorizontally).clickable(
                            onClick = {
                                //todo - check if can call it using events
                                viewModel.getAllChallenges()
                            }
                        )
                    )
                }
                else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()

                    )
                    {
                        items(
                            items = challengeWithParticipant!!,
                            key = { it.challenge.challengeId }) { item ->
                            val daysLeft =
                                if (item.challenge.challengeStatus == ChallengeStatus.ACTIVE) DateConversionFunctions.differenceDaysInTwoLong(
                                    item.challenge.startedAt,
                                    item.challenge.endedAt
                                )
                                else null
                            ChallengeCard(
                                viewModel = viewModel,
                                challengeId = item.challenge.challengeId,
                                status = item.challenge.challengeStatus.toString(),
                                challengeName = item.challenge.title,
                                daysLeft = daysLeft,
                                points = item.participant.totalPoints.toString()
                            )
                        }
                    }
                }
            }


        }


    }
}

@Composable
private fun ChallengeCard(
    viewModel: HomeViewModel,
    challengeId: String,
    challengeName: String,
    daysLeft: String?,
    points: String,
    status: String
){
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onClick = {
            viewModel.onEvent(UiEvent.Navigate(Screen.Challenge.createRoute(challengeId = challengeId)))
        },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = challengeName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Time Left: $daysLeft",
                style = MaterialTheme.typography.titleSmall
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Points: $points",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Status: $status",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}

@Composable
private fun HomeHeader(
    userName: String,
    viewModel: HomeViewModel
){

    Box(modifier =Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Hello $userName",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Your Challenges",
                    style = MaterialTheme.typography.headlineSmall
                )

            }
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Create Icon",
                modifier = Modifier.clickable(
                    onClick = {
                        viewModel.onEvent(UiEvent.Navigate(Screen.CreateChallenge.route))
                    }
                )

            )
            Spacer(modifier =Modifier.padding(4.dp))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Join Icon",
                modifier = Modifier.clickable(
                    onClick = {
                        viewModel.onEvent(UiEvent.Navigate(Screen.JoinChallenge.route))
                    }
                )
            )

        }
    }

}


@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen(rememberNavController(), hiltViewModel())
}
