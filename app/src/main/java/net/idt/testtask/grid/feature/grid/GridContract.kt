package net.idt.testtask.grid.feature.grid

import androidx.compose.runtime.Immutable

@Immutable
data class GridState(
    val columnNumber: Int,
    val items: List<GridItemState> = emptyList()
)

data class GridItemState(
    val id: Int,
    val title: String,
    val isSelected: Boolean,
    val isEditable: Boolean
)

sealed class GridAction {
    data object LoadMore: GridAction()

    data class OnItemClicked(val id: Int) : GridAction()

    data class OnItemDoubleClicked(val id: Int) : GridAction()

    data class OnTitleChanged(val id: Int, val title: String) : GridAction()
}
