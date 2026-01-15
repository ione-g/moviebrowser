package eone.grim.moviebrowser.presentation.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

interface MviIntent
interface MviState
interface MviEffect

abstract class MviViewModel<I : MviIntent, S : MviState, E : MviEffect>(
    initialState: S
) {
    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    protected val _effects = Channel<E>(Channel.BUFFERED)
    val effects: Flow<E> = _effects.receiveAsFlow()

    abstract fun onIntent(intent: I)
}