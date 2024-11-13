package com.example.quizy.presentation.quiz.dilog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizy.presentation.quiz.models.QuizIntent

@Composable
fun QuizStartDialog (
    onSelect: (QuizIntent)->Unit
){
    var inputText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {  },
        title = { Text(text = "Quiz game") },
        text = {
            Column {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    label = { Text("Enter id") }
                )

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    if(!inputText.isNullOrBlank()){
                        onSelect(QuizIntent.JoinRoom(inputText.toInt()))
                    }
                }) {
                    Text("Join")
                }
                Text(text = "or")
                Button(onClick = { onSelect(QuizIntent.CreateNewRoom) }) {
                    Text("Create room")
                }

            }
        },
        confirmButton = {

        },
        dismissButton = {

        }
    )
}
@Preview
@Composable
fun PreviewDialog(){
    QuizStartDialog {

    }
}