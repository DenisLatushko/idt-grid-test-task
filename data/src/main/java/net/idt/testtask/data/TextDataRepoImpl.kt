package net.idt.testtask.data

import net.idt.testtask.datasource.api.TextData
import net.idt.testtask.datasource.api.TextDataSource
import net.idt.testtask.domain.model.TextDomainModel
import net.idt.testtask.domain.repo.TextDataRepo

/**
 * See [TextDataRepo]
 */
internal class TextDataRepoImpl(
    private val textDataSource: TextDataSource,
    private val textDomainModelMapper: (TextData) -> TextDomainModel
) : TextDataRepo {

    override suspend fun getTexts(pageNumber: Int, maxItemsNumber: Int): List<TextDomainModel> =
        textDataSource.getTexts(pageNumber, maxItemsNumber)
            ?.map(textDomainModelMapper)
            ?: emptyList()

}
