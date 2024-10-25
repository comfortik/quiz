package com.example.quizy.presentation.common

enum class ErrorType(val message: String) {
    NETWORK_ERROR("Network error occurred, please try again."),
    TIMEOUT_ERROR("Request timed out, please try again."),
    UNKNOWN_ERROR("An unknown error occurred.")
}