package net.idt.testtask.domain.usecase

import net.idt.testtask.domain.model.TextDomainModel
import net.idt.testtask.domain.repo.TextDataRepo

/**
 * Call [TextDataRepo] to get texts and define the size of returned list
 */
class GetGridTextUseCase(private val textDataRepo: TextDataRepo) {

    suspend operator fun invoke(params: GetGridTextParams): List<TextDomainModel> =
        textDataRepo.getTexts(
            pageNumber = params.dataPage,
            maxItemsNumber = params.let { it.colNumber * it.rowNumber }
        )
}

/**
 * Parameters set for [GetGridTextUseCase]
 */
data class GetGridTextParams(
    val dataPage: Int,
    val colNumber: Int,
    val rowNumber: Int
)
