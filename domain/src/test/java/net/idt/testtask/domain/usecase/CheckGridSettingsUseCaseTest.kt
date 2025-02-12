package net.idt.testtask.domain.usecase

import net.idt.testtask.domain.model.GridSettingsCheckUpDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Tests for [CheckGridSettingsUseCase]
 */
@RunWith(Parameterized::class)
class CheckGridSettingsUseCaseTest(
    private val params: CheckGridSettingsParams,
    private val expectedResult: GridSettingsCheckUpDomainModel
) {

    private val useCase = CheckGridSettingsUseCase()

    @Test
    fun `given params when check grid settings then result contains correctness of them`() {
        val actualResult = useCase(params)

        assertEquals(expectedResult, actualResult)
    }

    companion object {
        @JvmStatic @Parameterized.Parameters(
            name = "Given params {0} when check grid settings then result {1}"
        )
        fun data() = listOf(
            arrayOf(
                params(colNumberText = "1", rowNumberText = "1"),
                result(isColNumberOk = true, isRowNumberOk = true)
            ),
            arrayOf(
                params(colNumberText = "6", rowNumberText = "1000"),
                result(isColNumberOk = true, isRowNumberOk = true)
            ),
            arrayOf(
                params(colNumberText = "7", rowNumberText = "1001"),
                result(isColNumberOk = false, isRowNumberOk = false)
            ),
            arrayOf(
                params(colNumberText = "0", rowNumberText = "0"),
                result(isColNumberOk = false, isRowNumberOk = false)
            ),
            arrayOf(
                params(colNumberText = "", rowNumberText = ""),
                result(isColNumberOk = false, isRowNumberOk = false)
            ),
        )

        private fun params(
            colNumberText: String,
            rowNumberText: String
        ) = CheckGridSettingsParams(
            colNumberText = colNumberText,
            rowNumberText = rowNumberText
        )

        private fun result(
            isColNumberOk: Boolean,
            isRowNumberOk: Boolean
        ) = GridSettingsCheckUpDomainModel(
            isColNumberOk = isColNumberOk,
            isRowNumberOk = isRowNumberOk
        )
    }
}
