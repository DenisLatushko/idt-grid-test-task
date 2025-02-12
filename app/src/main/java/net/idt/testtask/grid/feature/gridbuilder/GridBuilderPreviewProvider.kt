package net.idt.testtask.grid.feature.gridbuilder

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class GridBuilderPreviewProvider: PreviewParameterProvider<GridBuilderState> {

    override val values = sequenceOf(
        GridBuilderState(
            hasColNumberError = false,
            hasRowNumberError = false
        ),
        GridBuilderState(
            hasColNumberError = true,
            hasRowNumberError = true
        )
    )
}
