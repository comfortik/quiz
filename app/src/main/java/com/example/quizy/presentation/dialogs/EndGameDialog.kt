package com.example.quizy.presentation.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun EndGameDialog(score: Int, onDismiss: ()->Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Ok")
            }
        },
        text = {
            Text(text = "You win!\n Score: $score")
        },
    )
}