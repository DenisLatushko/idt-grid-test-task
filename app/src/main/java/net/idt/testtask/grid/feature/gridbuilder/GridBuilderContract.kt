package net.idt.testtask.grid.feature.gridbuilder

internal data class GridBuilderState(
    val hasColNumberError: Boolean = false,
    val hasRowNumberError: Boolean = false
)

internal sealed interface GridBuilderAction {
    class ApplySettings(
        val colNumberText: String,
        val rowNumberText: String
    ): GridBuilderAction
}
