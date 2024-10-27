package com.example.quizy.presentation.clicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizy.presentation.clicker.models.ClickerPlayerUiState
import com.example.quizy.presentation.common.screens.LoadingScreen

@Composable
fun ClickerScreen (
    onBackPressed: ()->Unit
){
    val viewModel: ClickerViewModel = hiltViewModel()
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    if (state.isloading) LoadingScreen()
    else CreateCLickerGame(clickerPlayers = state.players){viewModel.earnPoint()}
}



@Composable
fun CreateCLickerGame(
    clickerPlayers: List<ClickerPlayerUiState>,
    onEarnPointBtnCLick: ()->Unit
){

    val max = clickerPlayers.maxByOrNull { it.score } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = clickerPlayers.get(0).score.toString(),
            textAlign = TextAlign.Center)
        Box(modifier = Modifier
            .padding(16.dp)
            .weight(1f)
            .border(2.dp, Color.Black)
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp, start = 12.dp)
        ){
            LazyRow(
                verticalAlignment = Alignment.Bottom
            ) {
                items(clickerPlayers) { item ->
                    createRows(item, max.score)
                }
            }
        }
        Button(
            modifier = Modifier
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.DarkGray),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            onClick = { onEarnPointBtnCLick() }
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
            Text(text = "Click")
        }
    }
}

@Composable
fun createRows(player: ClickerPlayerUiState, max: Int){
    val high = player.score.toFloat()/max

    Column(modifier = Modifier
        .fillMaxHeight(high)
        .width(32.dp)
    ){
        Text(text = player.score.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .background(Color.DarkGray, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .fillMaxSize()
            ,


        )
    }
    Spacer(modifier = Modifier.width(12.dp))
}

@Preview
@Composable
fun PreviewCom(){
    val mockPlayers = listOf(
        ClickerPlayerUiState("alice", 12),
        ClickerPlayerUiState("bob", 32),
        ClickerPlayerUiState("john", 45),
        ClickerPlayerUiState("chack", 8),
        ClickerPlayerUiState("marat", 21),
    ).sortedByDescending {
        when(it.name ){
            "bob"->Int.MAX_VALUE
            else-> it.score
        }
    }
    CreateCLickerGame(clickerPlayers = mockPlayers) {

    }
}