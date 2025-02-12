package net.idt.testtask.datasource.impl

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.idt.testtask.datasource.api.TextData
import net.idt.testtask.datasource.api.TextDataSource

/**
 * See [TextDataSource]
 */
internal class TextDataSourceImpl(
    private val pageSize: Int,
    private val textGenerator: TextGenerator
) : TextDataSource {

    private val textData = mutableMapOf<Int, List<TextData>>()
    private var itemsCount: Int = textData.size
    private val mutex = Mutex()

    override suspend fun getTexts(pageNumber: Int, maxItemsNumber: Int): List<TextData>? =
        mutex.withLock { getText(pageNumber, maxItemsNumber) }

    private fun getText(pageNumber: Int, maxItemsNumber: Int): List<TextData>? {
        if (textData.containsKey(pageNumber)) return textData[pageNumber]
        if (itemsCount >= maxItemsNumber) return null

        val generatedTexts = generateTexts(
            startId = itemsCount,
            count = getRequiredTextNumberForPage(maxItemsNumber)
        )

        itemsCount += generatedTexts.size
        textData[pageNumber] = generatedTexts

        return generatedTexts
    }

    private fun getRequiredTextNumberForPage(maxItemsNumber: Int): Int {
        val itemsNumberRemaining = maxItemsNumber - itemsCount
        return if (itemsNumberRemaining >= pageSize) pageSize else itemsNumberRemaining
    }

    private fun generateTexts(startId: Int, count: Int): List<TextData> =
        List(count) { ind ->
            TextData(
                id = ind + startId,
                text = textGenerator.newRandomText()
            )
        }
}
