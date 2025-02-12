package net.idt.testtask.datasource.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import net.idt.testtask.datasource.api.TextData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

private const val MAX_PAGE_SIZE = 10
private const val MAX_ITEMS_NUMBER_ODD = 55
private const val MAX_ITEMS_NUMBER_EVEN = 50
private const val MAX_ITEMS_NUMBER_LOW_NUMBER = MAX_PAGE_SIZE - 2
private const val TEST_RANDOM_STRING = "1234567890"

class TextDataSourceImplTest {

    private val textGeneratorMock = mockk<TextGenerator> {
        every { newRandomText() } returns TEST_RANDOM_STRING
    }

    private val textDataSource = TextDataSourceImpl(
        textGenerator = textGeneratorMock,
        pageSize = MAX_PAGE_SIZE
    )

    @Test
    fun `given text generator when get text then generator invoked`() = runTest {
        textDataSource.getTexts(pageNumber = 1, maxItemsNumber = MAX_ITEMS_NUMBER_ODD)

        verify(exactly = MAX_PAGE_SIZE) {
            textGeneratorMock.newRandomText()
        }
    }

    @Test
    fun `given text generator when get text then final list has texts from generator with no changes`() =
        runTest {
            val expectedList = List(MAX_PAGE_SIZE) { TextData(id = it, text = TEST_RANDOM_STRING) }

            val textData = textDataSource.getTexts(
                pageNumber = 1,
                maxItemsNumber = MAX_ITEMS_NUMBER_ODD
            )

            assertNotNull(textData)
            assertEquals(expectedList, textData)
        }

    @Test
    fun `given items number more than page size when get text then return full page`() = runTest {
        val textData = textDataSource.getTexts(pageNumber = 1, maxItemsNumber = MAX_ITEMS_NUMBER_ODD)

        assertNotNull(textData)
        assert(textData!!.size == MAX_PAGE_SIZE)
    }

    @Test
    fun `given items number less than page size when get text then return not full page`() = runTest {
        val textData = textDataSource.getTexts(pageNumber = 1, maxItemsNumber = MAX_ITEMS_NUMBER_LOW_NUMBER)

        assertNotNull(textData)
        assert(textData!!.size == MAX_ITEMS_NUMBER_LOW_NUMBER)
    }

    @Test
    fun `given even final number of strings when call all pages then all texts returned`() = runTest {
        val pageNumber = MAX_ITEMS_NUMBER_EVEN / MAX_PAGE_SIZE
        val finalList = requestDataSource(pageNumber, MAX_ITEMS_NUMBER_EVEN)

        assertEquals(MAX_ITEMS_NUMBER_EVEN, finalList.size)
    }

    @Test
    fun `given odd final number of strings when call all pages then all texts returned`() = runTest {
        val pageNumber = MAX_ITEMS_NUMBER_ODD / MAX_PAGE_SIZE + 1
        val finalList = requestDataSource(pageNumber, MAX_ITEMS_NUMBER_ODD)

        assertEquals(MAX_ITEMS_NUMBER_ODD, finalList.size)
    }

    @Test
    fun `given even final number of strings when reach last page then no more text generated`() = runTest {
        val pageNumber = MAX_ITEMS_NUMBER_EVEN / MAX_PAGE_SIZE + 10
        val finalList = requestDataSource(pageNumber, MAX_ITEMS_NUMBER_EVEN)

        assertEquals(MAX_ITEMS_NUMBER_EVEN, finalList.size)
    }

    @Test
    fun `given odd final number of strings when reach last page then no more text generated`() = runTest {
        val pageNumber = MAX_ITEMS_NUMBER_ODD / MAX_PAGE_SIZE + 10
        val finalList = requestDataSource(pageNumber, MAX_ITEMS_NUMBER_ODD)

        assertEquals(MAX_ITEMS_NUMBER_ODD, finalList.size)
    }

    private suspend fun requestDataSource(pageNumber: Int, maxItemsNumber: Int): List<TextData>  {
        val finalList = mutableListOf<TextData>()

        (1 .. pageNumber).forEach { page ->
            textDataSource.getTexts(pageNumber = page, maxItemsNumber = maxItemsNumber)
                ?.run { finalList.addAll(this) }
        }

        return finalList
    }
}
