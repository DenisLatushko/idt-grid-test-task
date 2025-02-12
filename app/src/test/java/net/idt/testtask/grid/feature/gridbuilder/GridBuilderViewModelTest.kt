package net.idt.testtask.grid.feature.gridbuilder

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.idt.testtask.domain.model.GridSettingsCheckUpDomainModel
import net.idt.testtask.domain.usecase.CheckGridSettingsParams
import net.idt.testtask.domain.usecase.CheckGridSettingsUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Tests for [GridBuilderViewModel]
 */
@RunWith(Enclosed::class)
internal class GridBuilderViewModelTest {

    class GridBuilderViewModelTest {
        private val checkSettingsUseCaseMock = mockk<CheckGridSettingsUseCase>(relaxed = true)
        private val applySettingsAction by lazy { GridBuilderAction.ApplySettings("1", "1") }
        private val viewModel = GridBuilderViewModel(checkSettingsUseCaseMock)

        @Test
        fun `given apply settings action when perform action then check settings use case invoked`() {
            viewModel.onAction(applySettingsAction)

            verify(exactly = 1) {
                checkSettingsUseCaseMock.invoke(
                    CheckGridSettingsParams(
                        colNumberText = applySettingsAction.colNumberText,
                        rowNumberText = applySettingsAction.rowNumberText
                    )
                )
            }
        }
    }

    @RunWith(Parameterized::class)
    class CheckSettingsStateTest(
        private val checkUpDomainModel: GridSettingsCheckUpDomainModel,
        private val expectedState: GridBuilderState
    ) {
        private val checkUpDomainModelMock = mockk<GridSettingsCheckUpDomainModel>(relaxed = true)
        private val checkSettingsUseCaseMock = mockk<CheckGridSettingsUseCase> {
            every { this@mockk.invoke(any()) } returns checkUpDomainModelMock
        }
        private val applySettingsAction = GridBuilderAction.ApplySettings("1", "1")
        private val viewModel = GridBuilderViewModel(checkSettingsUseCaseMock)

        @Test
        fun `given domain model with grid check up result when received then it converted to state`() {
            every { checkSettingsUseCaseMock.invoke(any()) } returns checkUpDomainModel

            viewModel.onAction(applySettingsAction)

            assertEquals(expectedState, viewModel.state.value)
        }

        companion object {
            @JvmStatic @Parameterized.Parameters(
                name = "Given domain model {0} when check grid settings then state {1}"
            )
            fun data() = listOf(
                arrayOf(
                    domainModel(isColNumberOk = true, isRowNumberOk = true),
                    gridState(hasColNumberError = false, hasRowNumberError = false)
                ),
                arrayOf(
                    domainModel(isColNumberOk = false, isRowNumberOk = false),
                    gridState(hasColNumberError = true, hasRowNumberError = true)
                ),
                arrayOf(
                    domainModel(isColNumberOk = false, isRowNumberOk = true),
                    gridState(hasColNumberError = true, hasRowNumberError = false)
                ),
                arrayOf(
                    domainModel(isColNumberOk = true, isRowNumberOk = false),
                    gridState(hasColNumberError = false, hasRowNumberError = true)
                )
            )

            private fun domainModel(
                isColNumberOk: Boolean,
                isRowNumberOk: Boolean
            ) = GridSettingsCheckUpDomainModel(
                isColNumberOk = isColNumberOk,
                isRowNumberOk = isRowNumberOk
            )

            private fun gridState(
                hasColNumberError: Boolean,
                hasRowNumberError: Boolean
            ) = GridBuilderState(
                hasColNumberError = hasColNumberError,
                hasRowNumberError = hasRowNumberError
            )
        }
    }
}
