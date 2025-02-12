package net.idt.testtask.grid.feature.grid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
internal fun Grid(
    modifier: Modifier = Modifier,
    state: GridState,
    onAction: (GridAction) -> Unit
) {
    val scrollState = rememberLazyGridState()
    val fetchNextPage: Boolean by derivedStateOf {
            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo
                .lastOrNull()
                ?.index
                ?: return@derivedStateOf false
            return@derivedStateOf lastDisplayedIndex >= state.items.size - 10
        }

    LaunchedEffect(key1 = fetchNextPage) {
        if (fetchNextPage) onAction(GridAction.LoadMore)
    }

    LazyVerticalGrid(
        modifier = modifier.background(color = Color.LightGray),
        columns = GridCells.Fixed(state.columnNumber),
        state = scrollState
    ) {
        items(
            count = state.items.size,
            key = { state.items[it].id }
        ) { ind ->
            GridItem(
                state = state.items[ind],
                onAction = onAction
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GridItem(
    modifier: Modifier = Modifier,
    state: GridItemState,
    onAction: (GridAction) -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 15.dp)
            .drawBehind { drawRect(color = if (state.isSelected) Green else White) }
            .border(2.dp, Black, RectangleShape)
            .combinedClickable(
                onClick = { onAction(GridAction.OnItemClicked(state.id)) },
                onDoubleClick = { onAction(GridAction.OnItemDoubleClicked(state.id)) }
            )
    ) {
        if (state.isEditable) {
            var text by remember { mutableStateOf(state.title) }
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxSize(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { onAction(GridAction.OnTitleChanged(state.id, text)) }
                )
            )
        } else {
            Text(
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                text = state.title,
                color = Black
            )
        }
    }
}
