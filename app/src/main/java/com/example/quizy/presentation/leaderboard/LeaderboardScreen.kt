package com.example.quizy.presentation.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizy.presentation.common.screens.LoadingScreen
import com.example.quizy.presentation.leaderboard.models.LeaderboardAction
import com.example.quizy.presentation.leaderboard.models.PlayerCardUIState
import com.example.quizy.presentation.utlis.ByteArrayToBitmapUtil


@Composable
fun LeaderboardScreen (
    onBackPressed: ()->Unit,
    onError: (String)->Unit
){

    val viewModel: LeaderboardViewModel = hiltViewModel()
    val state by viewModel.screenState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.init()
    }

    if (state.isLoading) LoadingScreen()
    else CreateLeaderboardColumn(state.players)

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            when (action) {
                is LeaderboardAction.ShowErrorMessage -> {
                    onError(action.error.message)
                }
            }
        }
    }


}

@Composable
fun CreateLeaderboardColumn(players: List<PlayerCardUIState>) {
    LazyColumn {
        items(players){ player ->
            CreatePlayerCard(playerCard = player)
        }
    }
}

@Composable
private fun CreatePlayerCard(playerCard: PlayerCardUIState){
    Spacer(modifier = Modifier.height(8.dp))
    Box(modifier = Modifier.padding(12.dp)
        .background(Color.DarkGray, RoundedCornerShape(12.dp))
        .padding(12.dp)){
        Row(modifier = Modifier.fillMaxWidth()){
            Image(
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp),
                bitmap = ByteArrayToBitmapUtil(playerCard.image).asImageBitmap(),
                contentDescription = "")
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                Text(text = playerCard.name)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = playerCard.totalScore.toString())
            }

        }
    }

}

