package net.idt.testtask.grid.utils.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * A separate MVI abstraction to manage MVI side effects
 */
interface MviSideEffectController<T> {
    val sideEffect: Flow<T>

    fun CoroutineScope.emitSideEffect(effect: T)
}

/**
 * Subscribe a side effect [Flow] what is initially can be a kotlin [Channel] lie in
 * [BaseMviSideEffectController]. Based on the lifecycle state the function
 * subscribes immediately to the [Flow]
 */
@Composable
fun <T> CollectAsSideEffect(
    flow: Flow<T>,
    startLifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onSideEffect: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(startLifecycleState) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onSideEffect)
            }
        }
    }
}