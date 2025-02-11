package net.idt.testtask.domain.model

/**
 * A domain model which contains a result of grid settings check up
 *
 * @property isColNumberOk indicates that the number of columns is correct
 * @property isRowNumberOk indicates that the number of rows is correct
 */
data class GridSettingsCheckUpDomainModel(
    val isColNumberOk: Boolean,
    val isRowNumberOk: Boolean
)
