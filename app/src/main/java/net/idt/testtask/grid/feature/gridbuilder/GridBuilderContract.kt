package net.idt.testtask.grid.feature.gridbuilder

data class GridBuilderState(
    val hasColNumberError: Boolean = false,
    val hasRowNumberError: Boolean = false
)

sealed interface GridBuilderAction {
    class ApplySettings(
        val colNumberText: String,
        val rowNumberText: String
    ): GridBuilderAction
}
