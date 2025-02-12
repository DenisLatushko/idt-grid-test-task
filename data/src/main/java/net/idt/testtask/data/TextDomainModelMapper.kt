package net.idt.testtask.data

import net.idt.testtask.datasource.api.TextData
import net.idt.testtask.domain.model.TextDomainModel

/**
 * Maps [TextData] to [TextDomainModel]
 */
class TextDomainModelMapper: (TextData) -> TextDomainModel {
    override fun invoke(textData: TextData): TextDomainModel = textData.let {
        TextDomainModel(id = it.id, text = it.text)
    }
}
