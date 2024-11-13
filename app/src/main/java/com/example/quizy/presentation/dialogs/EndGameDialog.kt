package com.example.quizy.presentation.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun EndGameDialog(name: String = "", score: Int, onDismiss: ()->Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Ok")
            }
        },
        text = {
            val nameWinner = if(name=="")"You" else name
            Text(text = "$nameWinner win!\n Score: $score")
        },
    )
}