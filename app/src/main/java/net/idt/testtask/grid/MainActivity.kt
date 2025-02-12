package net.idt.testtask.grid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.idt.testtask.grid.feature.grid.Grid
import net.idt.testtask.grid.feature.grid.GridViewModel
import net.idt.testtask.grid.feature.grid.GridViewModelInitParams
import net.idt.testtask.grid.ui.theme.IDTGridTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IDTGridTheme {
                val viewModel = koinViewModel<GridViewModel>(
                    parameters = { parametersOf(GridViewModelInitParams(3, 10)) }
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                Grid(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}
