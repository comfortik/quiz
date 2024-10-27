package com.example.quizy.presentation.pairs.model

import com.example.quizy.presentation.common.BaseAction

sealed class PairsAction(): BaseAction{
    data class EndGameDialog(val score: Int):PairsAction()
}