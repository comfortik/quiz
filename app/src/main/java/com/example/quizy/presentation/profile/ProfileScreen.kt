package com.example.quizy.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizy.presentation.common.screens.LoadingScreen


@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = hiltViewModel()
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.init()
    }
    
    if(state.isLoading) LoadingScreen()
    else CreateProfile(profile = state)
    
}
@Composable
private fun CreateProfile(profile: ProfileUiState){
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Column {
            Image(bitmap = profile.image.asImageBitmap(),
                contentDescription =null,
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Score: ${profile.total_score}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
                )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "Good ${profile.hello}!")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = profile.name,
                )
        }
    }


}

@Preview
@Composable
fun createPreview(){
    CreateProfile(profile= ProfileUiState(false, "bob", total_score = "12", hello = "day"))
}