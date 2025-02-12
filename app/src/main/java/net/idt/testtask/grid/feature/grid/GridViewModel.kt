package net.idt.testtask.grid.feature.grid

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.idt.testtask.domain.model.TextDomainModel
import net.idt.testtask.domain.usecase.GetGridTextParams
import net.idt.testtask.domain.usecase.GetGridTextUseCase
import net.idt.testtask.grid.utils.architecture.MVIViewModel

/**
 * A view model to interact with grid screen
 */
internal class GridViewModel(
    private val initParams: GridViewModelInitParams,
    private val getGridTextUseCase: GetGridTextUseCase,
    private val gridItemStateMapper: (TextDomainModel) -> GridItemState,
    private val asyncDispatcher: CoroutineDispatcher = Dispatchers.Default
) : MVIViewModel<GridState, GridAction>() {

    private var pagesNumber = 0
    private val _state = MutableStateFlow(GridState(columnNumber = initParams.colNumber))
    override val state: StateFlow<GridState> = _state.asStateFlow()

    init {
        onAction(GridAction.LoadMore)
    }

    override fun onAction(action: GridAction) {
        when (action) {
            is GridAction.LoadMore -> loadMore()
            is GridAction.OnItemClicked -> onItemClicked(action.id)
            is GridAction.OnItemDoubleClicked -> onItemDoubleClicked(action.id)
            is GridAction.OnTitleChanged -> onTitleChanged(action.id, action.title)
        }
    }

    private fun onItemDoubleClicked(id: Int) {
        val newState = _state.value.copy(
            items = _state.value.items.map {
                if (it.id == id) it.copy(isEditable = !it.isEditable) else it
            }
        )

        _state.update { newState }
    }

    private fun onTitleChanged(id: Int, newTitle: String) {
        val newState = _state.value.copy(
            items = _state.value.items.map {
                if (it.id == id) {
                    it.copy(
                        title = newTitle,
                        isEditable = false
                    )
                } else it
            }
        )
        _state.update { newState }
    }

    private fun onItemClicked(id: Int) {
        val newState = _state.value.copy(
            items = _state.value.items.map {
                if (it.id == id) it.copy(isSelected = !it.isSelected) else it
            }
        )
        _state.update { newState }
    }

    private fun loadMore() {
        viewModelScope.launch {
            val newItems = withContext(asyncDispatcher) {
                getGridTextUseCase.invoke(
                    params = GetGridTextParams(
                        dataPage = ++pagesNumber,
                        colNumber = initParams.colNumber,
                        rowNumber = initParams.rowNumber
                    )
                ).map(gridItemStateMapper)
            }

            if (isActive) {
                val newState = _state.value
                    .copy(items = _state.value.items + newItems)

                _state.update { newState }
            }
        }
    }
}

/**
 * Initial parameters for [GridViewModel] which comes outside of the feature
 */
class GridViewModelInitParams(
    val colNumber: Int,
    val rowNumber: Int
)

