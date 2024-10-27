package com.example.quizy.presentation.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.quizy.presentation.games.models.GameItemUiState
import com.example.quizy.presentation.games.models.GamesActions


@Composable
fun GamesScreen (
    onGameClicked: (String)->Unit
){
    val viewModel: GamesViewModel = hiltViewModel()
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    if(state.isLoading) LoadingScreen()
    else CreateGameColumn(games = state.games) {name->
        onGameClicked(name)
    }

    LaunchedEffect(Unit) {
        viewModel.init()
    }

}

@Composable
fun CreateGameColumn(games: List<GameItemUiState>, onGameClicked: (String) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(games){ game->
            CreateItemGame(game = game) { name->
                onGameClicked(name)
            }
        }
    }
}

@Composable
fun CreateItemGame(game: GameItemUiState, onGameClicked: (String) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onGameClicked(game.name) }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(Color.DarkGray)
            .padding(12.dp)
    ) {
        Image(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp),
            bitmap = game.image.asImageBitmap(),
            contentDescription =null,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = game.name)
    }
}