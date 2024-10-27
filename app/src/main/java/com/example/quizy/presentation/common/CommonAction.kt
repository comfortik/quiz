package com.example.quizy.presentation.common

sealed class CommonAction : BaseAction {
    data class ShowErrorDialog(val error: ErrorType) : CommonAction()
}