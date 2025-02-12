package net.idt.testtask.grid.utils.architecture

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * An MVI side effect controller based on [Channel]
 */
class BaseMviSideEffectController<T> : MviSideEffectController<T> {
    private val _sideEffect = Channel<T>()
    override val sideEffect = _sideEffect.receiveAsFlow()

    override fun CoroutineScope.emitSideEffect(effect: T) {
        launch {
            _sideEffect.send(effect)
        }
    }
}

fun <T> baseSideEffectController(): MviSideEffectController<T> = BaseMviSideEffectController()