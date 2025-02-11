package net.idt.testtask.grid.utils.architecture

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Base class for all [ViewModel]
 */
internal abstract class MVIViewModel<out State, in Action>: ViewModel() {
    abstract val state: StateFlow<State>

    abstract fun onAction(action: Action)
}
