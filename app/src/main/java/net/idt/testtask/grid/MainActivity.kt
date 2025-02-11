package net.idt.testtask.grid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.idt.testtask.grid.feature.gridbuilder.GridBuilder
import net.idt.testtask.grid.feature.gridbuilder.GridBuilderViewModel
import net.idt.testtask.grid.ui.theme.IDTGridTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IDTGridTheme {
                val viewModel = koinViewModel<GridBuilderViewModel>()
                val state = viewModel.state.collectAsStateWithLifecycle()

                GridBuilder(
                    modifier = Modifier.fillMaxSize(),
                    state = state.value,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}
