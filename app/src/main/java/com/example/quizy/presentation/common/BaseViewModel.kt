package com.example.quizy.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : BaseState, Action : BaseAction> : ViewModel() {

    protected val _currentState by lazy { MutableStateFlow(createInitState()) }
    protected val state: State get() = _currentState.value
    val screenState: StateFlow<State> = _currentState.asStateFlow()

    protected val _action = MutableSharedFlow<Action>()
    val actions: SharedFlow<Action> get() = _action.asSharedFlow()

    protected abstract fun createInitState(): State

    protected fun sendAction(action: Action) {
        viewModelScope.launch {
            _action.emit(action) // используем emit вместо send
        }
    }
}
