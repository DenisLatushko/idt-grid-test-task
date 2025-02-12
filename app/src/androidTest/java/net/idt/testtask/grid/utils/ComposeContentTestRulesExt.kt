package net.idt.testtask.grid.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import net.idt.testtask.grid.ui.theme.IDTGridTheme

fun ComposeContentTestRule.setContentWithTheme(
    content: @Composable () -> Unit
) {
    setContent {
        IDTGridTheme {
            content()
        }
    }
}