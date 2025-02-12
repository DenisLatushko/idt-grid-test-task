package net.idt.testtask.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.idt.testtask.datasource.api.TextData
import net.idt.testtask.datasource.api.TextDataSource
import net.idt.testtask.domain.model.TextDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests for [TextDataRepoImpl]
 */
class TextDataRepoImplTest {

    private val textDataMock by lazy {
        mockk<TextData>(relaxed = true) {
            every { id } returns 1
            every { text } returns "test"
        }
    }

    private val textDomainMock by lazy {
        mockk<TextDomainModel>(relaxed = true) {
            every { id } returns 1
            every { text } returns "test"
        }
    }

    private val textDataList by lazy { List(5) { textDataMock } }

    private val textDomainModelMapperMock = mockk<TextDomainModelMapper>(relaxed = true)
    private val textDataSourceMock = mockk<TextDataSource>(relaxed = true)

    private val textDataRepo = TextDataRepoImpl(
        textDataSource = textDataSourceMock,
        textDomainModelMapper = textDomainModelMapperMock
    )

    @Test
    fun `given text data source when get texts then data source and mapper invoked`() = runTest {
        coEvery { textDataSourceMock.getTexts(any(), any()) } returns textDataList

        textDataRepo.getTexts(pageNumber = 1, maxItemsNumber = 10)

        coVerify (exactly = 1) { textDataSourceMock.getTexts(pageNumber = 1, maxItemsNumber = 10) }
        coVerify (exactly = textDataList.size) { textDomainModelMapperMock.invoke(textDataMock) }
    }

    @Test
    fun `given text data source when data source returns null then data source invoked and mapper skipped`() = runTest {
        coEvery { textDataSourceMock.getTexts(any(), any()) } returns null

        textDataRepo.getTexts(pageNumber = 1, maxItemsNumber = 10)

        coVerify(exactly = 1) { textDataSourceMock.getTexts(pageNumber = 1, maxItemsNumber = 10) }
        coVerify(exactly = 0) { textDomainModelMapperMock.invoke(any()) }
    }

    @Test
    fun `given text data when it is mapped to domain model then it is passed with no changes`() = runTest {
        coEvery { textDataSourceMock.getTexts(any(), any()) } returns textDataList
        coEvery { textDomainModelMapperMock.invoke(textDataMock) } returns textDomainMock

        val domainModelList = textDataRepo.getTexts(pageNumber = 1, maxItemsNumber = 10)

        assertEquals(List(5) { textDomainMock }, domainModelList)
    }
}
