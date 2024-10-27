package com.example.quizy.presentation.pairs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizy.R
import com.example.quizy.presentation.common.screens.LoadingScreen
import com.example.quizy.presentation.pairs.model.CardUiState
import com.example.quizy.presentation.pairs.model.PairsAction


@Composable
fun PairsScreen(
    onBackPressed: ()->Unit,
    onEndGame: (Int)->Unit
){
    val viewModel: PairsViewModel = hiltViewModel()
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.actions.collect{ action->
            when(action){
                is PairsAction.EndGameDialog ->onEndGame(action.score)
            }

        }
        
    }

    if(state.isLoading) LoadingScreen()
    else CreateGrid(rows = 3, cards = state.cards, score = state.score) {num, id->
        viewModel.checkCard(num, id)
    }


}


val image: Bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)

//@Preview
//@Composable
//fun PreviewCard() {
//    CreateGrid(
//        rows = 3,
//        cards = List(9) { CardUiState(it, true, image = image) }
//    ) {
//
//    }
//}

@Composable
fun CreateGrid(rows: Int, cards: List<CardUiState>, score: Int,onCardClick: (Int, Int) -> Unit) {
    Column {
        Text(text = "Score: $score", modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(rows),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cards) { item ->
                CreateCard(card = item){n, id->
                    onCardClick(n, id)
                }
            }
        }
    }
}

@Composable
fun CreateCard(card: CardUiState, onCardClick: (Int, Int) -> Unit) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clickable { onCardClick(card.num, card.id) }
    ) {
        if (card.isVisible){
            Image(
                bitmap = card.image.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
        }else{
            Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
        }

    }


}