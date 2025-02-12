package net.idt.testtask.grid.feature.gridbuilder

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.idt.testtask.grid.feature.gridbuilder.GridBuilderSideEffect.NavigationSideEffect
import net.idt.testtask.grid.utils.architecture.CollectAsSideEffect
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun GridBuilderEntryPoint(onNavigate: (NavigationSideEffect) -> Unit = {}) {
    val viewModel = koinViewModel<GridBuilderViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectAsSideEffect(viewModel.sideEffect) { effect ->
        when(effect) {
            is NavigationSideEffect -> onNavigate(effect)
        }
    }

    GridBuilderScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onAction = viewModel::onAction
    )
}
