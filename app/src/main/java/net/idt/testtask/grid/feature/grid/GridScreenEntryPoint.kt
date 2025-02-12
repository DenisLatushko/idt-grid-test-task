package net.idt.testtask.grid.feature.grid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.idt.testtask.grid.ui.navigation.NavRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun GridScreenEntryPoint(
    colNumber: Int,
    rowNumber: Int
) {
    val viewModel = koinViewModel<GridViewModel>(parameters = {
        parametersOf(GridViewModelInitParams(colNumber = colNumber, rowNumber = rowNumber))
    })
    val state by viewModel.state.collectAsStateWithLifecycle()

    Grid(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onAction = viewModel::onAction
    )
}
