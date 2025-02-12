package net.idt.testtask.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.idt.testtask.domain.model.TextDomainModel
import net.idt.testtask.domain.repo.TextDataRepo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private const val TEST_PAGE_NUMBER = 1
private const val TEST_ROW_NUMBER = 5
private const val TEST_COL_NUMBER = 6

class GetGridTextUseCaseTest {

    private val textDataRepoMock = mockk<TextDataRepo>(relaxed = true)

    private val domainModel by lazy {
        TextDomainModel(
            id = 1,
            text = "text"
        )
    }
    private val domainModelList by lazy {
        List(5) { domainModel }
    }

    private val useCaseParams = GetGridTextParams(
        dataPage = TEST_PAGE_NUMBER,
        colNumber = TEST_ROW_NUMBER,
        rowNumber = TEST_COL_NUMBER
    )

    private val useCase = GetGridTextUseCase(textDataRepo = textDataRepoMock)

    @Test
    fun `given use case when invoked then text repo invoked as well`() = runTest {
        useCase.invoke(useCaseParams)

        coVerify(exactly = 1) {
            textDataRepoMock.getTexts(
                pageNumber = useCaseParams.dataPage,
                maxItemsNumber = useCaseParams.let { it.colNumber * it.rowNumber}
            )
        }
    }

    @Test
    fun `given use case when invoked then data received from text repo without changes`() = runTest {
        coEvery { textDataRepoMock.getTexts(any(), any()) } returns domainModelList

        val actualDomainModelList = useCase.invoke(useCaseParams)

        assertEquals(domainModelList, actualDomainModelList)
    }

    @Test
    fun `given empty data when text data invoked then empty data returned from use case`() = runTest {
        coEvery { textDataRepoMock.getTexts(any(), any()) } returns emptyList()

        val actualDomainModelList = useCase.invoke(useCaseParams)

        assertTrue(actualDomainModelList.isEmpty())
    }
}
