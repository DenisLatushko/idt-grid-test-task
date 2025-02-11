package net.idt.testtask.grid.feature.gridbuilder

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.idt.testtask.domain.usecase.CheckGridSettingsParams
import net.idt.testtask.domain.usecase.CheckGridSettingsUseCase
import net.idt.testtask.grid.feature.gridbuilder.GridBuilderAction.ApplySettings
import net.idt.testtask.grid.utils.architecture.MVIViewModel

/**
 * A [MVIViewModel] to allow grid builder screen react for
 * user interactions like enter row and col number
 *
 * @property checkGridSettingsUseCase use case to check grid settings
 */
internal class GridBuilderViewModel(
    private val checkGridSettingsUseCase: CheckGridSettingsUseCase
) : MVIViewModel<GridBuilderState, GridBuilderAction>() {

    private val _state = MutableStateFlow(GridBuilderState())
    override val state = _state.asStateFlow()

    override fun onAction(action: GridBuilderAction) {
        when(action) {
            is ApplySettings -> onApplyGridSettings(action)
        }
    }

    private fun onApplyGridSettings(action: ApplySettings) {
        val gridSettingsDomainModel = checkGridSettingsUseCase(
            params = CheckGridSettingsParams(
                colNumberText = action.colNumberText,
                rowNumberText = action.rowNumberText
            )
        )

        val newState = _state.value.copy(
            hasColNumberError = !gridSettingsDomainModel.isColNumberOk,
            hasRowNumberError = !gridSettingsDomainModel.isRowNumberOk
        )

        _state.update { newState }
    }
}
