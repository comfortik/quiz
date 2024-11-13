package com.example.quizy.presentation.quiz

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizy.presentation.common.screens.LoadingScreen
import com.example.quizy.presentation.quiz.dilog.QuizStartDialog
import com.example.quizy.presentation.quiz.models.QuizAction
import com.example.quizy.presentation.quiz.models.QuizIntent
import com.example.quizy.presentation.quiz.models.QuizUIState


@Composable
fun QuizScreen (
    onToast: ()->Unit
){
    val viewModel:QuizViewModel  = hiltViewModel()
    val state = viewModel.screenState.collectAsState()
    val context = LocalContext.current

    if(state.value.isLoading) LoadingScreen()
    else if(state.value.isShowAlert) ShowStartAlert {intent->
        viewModel.handleIntent(intent)
    }
    else CreateUi(state.value){
            viewModel.updateReady(it)
         }


    LaunchedEffect(key1 = viewModel) {
        viewModel.actions.collect{action->
            when(action){
                is QuizAction.ShowErrorToast->{
                    Log.d("quiz", "quizquizuqiuzi")
                    Toast.makeText(context, "Please, enter id", Toast.LENGTH_SHORT).show()
                }
            }
        }        
    }
}

@Composable
fun ShowStartAlert(onClick: (QuizIntent)->Unit){
    QuizStartDialog {intent->
        onClick(intent)
    }
}


@Preview(showBackground = true)
@Composable
fun previewMain(){
    CreateUi(state = QuizUIState(roomId = 1, question = "Question?",
        answer = listOf(Pair("blabla1", true), Pair("22222", false), Pair("3333b;a", false), Pair("last", false))
    )){

    }
}


@Composable
fun CreateUi(state: QuizUIState, onClick: (Boolean) -> Unit) {
    Column (
        modifier =
        Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Room id: ${state.roomId}")
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Players: ${state.players}")
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = state.question, style = TextStyle(fontSize = 24.sp))
        Spacer(modifier = Modifier.height(24.dp))
        state.answer.forEach { answer->
            CreateButton(answer = answer) {
                onClick(it)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { onClick(false) }) {
            Text(text = "I ready")
        }
        Spacer(modifier = Modifier.height(24.dp))
    }

}

@Composable
fun CreateButton(answer: Pair<String, Boolean>, onClick: (Boolean) -> Unit){
    Button(onClick = {
        onClick(answer.second)
    },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = answer.first)
    }
}
