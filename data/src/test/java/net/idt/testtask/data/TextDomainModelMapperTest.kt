package net.idt.testtask.data

import net.idt.testtask.datasource.api.TextData
import org.junit.Test

private const val TEST_TEXT = "test"

/**
 * Tests for [TextDomainModelMapper]
 */
class TextDomainModelMapperTest {

    private val mapper = TextDomainModelMapper()

    @Test
    fun `given text data when map then domain model created`() {
        val textData = TextData(id = 1, text = TEST_TEXT)

        val actualModel = mapper(textData)

        assert(actualModel.id == textData.id)
        assert(actualModel.text == textData.text)
    }
}
