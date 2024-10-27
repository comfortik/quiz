package com.example.quizy.presentation.choosePlayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizy.presentation.choosePlayer.models.ChoosePlayerAction
import com.example.quizy.presentation.choosePlayer.models.ChoosePlayerUiState
import com.example.quizy.presentation.common.screens.LoadingScreen
import com.example.quizy.presentation.leaderboard.models.LeaderboardAction
import com.example.quizy.presentation.utlis.ByteArrayToBitmapUtil


@Composable
fun ChoosePlayerScreen(
    goToLeaderBoard: ()->Unit
) {
    val viewModel: ChoosePlayerViewModel  = hiltViewModel()
    val state by viewModel.screenState.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.init()
    }

    if (state.isLoading) LoadingScreen()
    else CreateColumnChoose(players = state.players) {id->
        viewModel.setUserIdToSharedPrefs(id)
    }

    LaunchedEffect(viewModel) {
        viewModel.actions.collect { action ->
            when (action) {
                ChoosePlayerAction.GoToLeaderboard->goToLeaderBoard()
            }
        }
    }

}

@Composable
private fun CreateColumnChoose(
    players: List<ChoosePlayerUiState>,
    onPlayerClick: (Int)->Unit
){
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
    ) {
        items(players){player->
            CreatePlayerItem(player = player) {id->
                onPlayerClick(id)
            }
        }
    }
}

@Composable
fun CreatePlayerItem(player: ChoosePlayerUiState, onPlayerClick: (Int) -> Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlayerClick(player.id) }
            .padding(12.dp)
            .background(
                Color.DarkGray, RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ){
        Image(bitmap = ByteArrayToBitmapUtil(player.image).asImageBitmap(),
            contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = player.name)
    }
}

