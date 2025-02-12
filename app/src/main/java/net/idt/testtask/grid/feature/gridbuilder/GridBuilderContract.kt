package net.idt.testtask.grid.feature.gridbuilder

internal data class GridBuilderState(
    val hasColNumberError: Boolean = false,
    val hasRowNumberError: Boolean = false
)

internal sealed interface GridBuilderAction {
    data class ApplySettings(
        val colNumberText: String,
        val rowNumberText: String
    ): GridBuilderAction
}

internal sealed interface GridBuilderSideEffect {

    sealed interface NavigationSideEffect: GridBuilderSideEffect {

        data class GridReady(
            val colNumber: Int,
            val rowNumber: Int
        ): NavigationSideEffect
    }
}
