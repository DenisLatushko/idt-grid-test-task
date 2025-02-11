package net.idt.testtask.domain.usecase

import net.idt.testtask.domain.model.GridSettingsCheckUpDomainModel

private const val MIN_COLUMNS_NUMBER = 1
private const val MAX_COLUMNS_NUMBER = 6
private const val MIN_ROWS_NUMBER = 1
private const val MAX_ROWS_NUMBER = 1000

/**
 * Validate if all required grid params are correct
 */
class CheckGridSettingsUseCase {

    operator fun invoke(params: CheckGridSettingsParams): GridSettingsCheckUpDomainModel =
        GridSettingsCheckUpDomainModel(
            isColNumberOk = isColNumberCorrect(params.colNumberText),
            isRowNumberOk = isRowNumberCorrect(params.rowNumberText)
        )

    private fun isColNumberCorrect(colNumberText: String): Boolean = colNumberText.let {
        val colNumber = it.toIntOrNull() ?: 0
        colNumber in MIN_COLUMNS_NUMBER..MAX_COLUMNS_NUMBER
    }

    private fun isRowNumberCorrect(rowNumberText: String): Boolean = rowNumberText.let {
        val rowNumber = it.toIntOrNull() ?: 0
        rowNumber in MIN_ROWS_NUMBER..MAX_ROWS_NUMBER
    }
}

data class CheckGridSettingsParams(
    val colNumberText: String,
    val rowNumberText: String
)
