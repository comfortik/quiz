package com.example.quizy.presentation.common

object ErrorHandlerProvider {
    private var errorHandler: ErrorHandler? = null

    fun setErrorHandler(handler: ErrorHandler) {
        errorHandler = handler
    }

    fun getErrorHandler(): ErrorHandler {
        return errorHandler ?: throw IllegalStateException("ErrorHandler not initialized")
    }
}
