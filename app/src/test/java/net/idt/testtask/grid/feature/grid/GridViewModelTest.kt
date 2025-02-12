package net.idt.testtask.grid.feature.grid

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.idt.testtask.domain.model.TextDomainModel
import net.idt.testtask.domain.usecase.GetGridTextParams
import net.idt.testtask.domain.usecase.GetGridTextUseCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

private const val TEST_COL_NUMBER = 3
private const val TEST_ROW_NUMBER = 500
private const val TEST_TEXT = "test"

/**
 * Tests for [GridViewModel]
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GridViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val getGridTextUseCaseMock = mockk<GetGridTextUseCase>(relaxed = true)
    private val itemStateMapperMock = mockk<GridItemStateMapper>(relaxed = true)

    private val domainModel by lazy { TextDomainModel(id = 1, text = TEST_TEXT) }
    private val textDomainModelList by lazy { List(3) { domainModel } }

    private val itemState by lazy {
        GridItemState(
            id = domainModel.id,
            title = domainModel.text,
            isSelected = false,
            isEditable = false
        )
    }

    private lateinit var viewModel: GridViewModel

    @Before
    fun `set up`() {
        Dispatchers.setMain(dispatcher)

        viewModel = GridViewModel(
            initParams = GridViewModelInitParams(
                colNumber = TEST_COL_NUMBER,
                rowNumber = TEST_ROW_NUMBER
            ),
            getGridTextUseCase = getGridTextUseCaseMock,
            gridItemStateMapper = itemStateMapperMock,
            asyncDispatcher = dispatcher
        )
    }

    @After
    fun `tear down`() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given view model when init then get text use case invoked`() = runTest {
        coVerify(exactly = 1) {
            getGridTextUseCaseMock.invoke(
                params = GetGridTextParams(
                    dataPage = 1,
                    colNumber = TEST_COL_NUMBER,
                    rowNumber = TEST_ROW_NUMBER
                )
            )
        }
    }

    @Test
    fun `given view model when init then first state no items but have cols number`() {
        val state = viewModel.state.value

        assertEquals(TEST_COL_NUMBER, state.columnNumber)
        assertTrue(state.items.isEmpty())
    }

    @Test
    fun `given load more action when invoked then get text use case invoked and state mapper`() {
        coEvery { getGridTextUseCaseMock.invoke(any()) } returns textDomainModelList

        viewModel.onAction(GridAction.LoadMore)

        coVerify(exactly = 1) {
            getGridTextUseCaseMock .invoke(
                params = GetGridTextParams(
                    dataPage = 2,
                    colNumber = TEST_COL_NUMBER,
                    rowNumber = TEST_ROW_NUMBER
                )
            )
        }

        coVerify(exactly = textDomainModelList.size) {
            itemStateMapperMock.invoke(domainModel)
        }
    }

    @Test
    fun `given load more action when use case returned empty data then mapper not invoked`() {
        coEvery { getGridTextUseCaseMock.invoke(any()) } returns emptyList()

        viewModel.onAction(GridAction.LoadMore)

        coVerify(exactly = 0) { itemStateMapperMock.invoke(domainModel) }
    }

    @Test
    fun `given load more action when use case returned data state changed`() = runTest{
        coEvery { getGridTextUseCaseMock.invoke(any()) } returns textDomainModelList
        every { itemStateMapperMock.invoke(any()) } returns itemState

        val mapperStateList = textDomainModelList.map { itemState }

        viewModel.onAction(GridAction.LoadMore)
        assertEquals(TEST_COL_NUMBER, viewModel.state.value.columnNumber)
        assertEquals(mapperStateList, viewModel.state.value.items)

        viewModel.onAction(GridAction.LoadMore)
        assertEquals(TEST_COL_NUMBER, viewModel.state.value.columnNumber)
        assertEquals(mapperStateList + mapperStateList, viewModel.state.value.items)
    }

    @Test
    fun `given load more action when use case returned empty data then state not changed`() {
        coEvery { getGridTextUseCaseMock.invoke(any()) } returns emptyList()
        every { itemStateMapperMock.invoke(any()) } returns itemState

        viewModel.onAction(GridAction.LoadMore)
        assertEquals(TEST_COL_NUMBER, viewModel.state.value.columnNumber)
        assertTrue(viewModel.state.value.items.isEmpty())

        viewModel.onAction(GridAction.LoadMore)
        assertEquals(TEST_COL_NUMBER, viewModel.state.value.columnNumber)
        assertTrue(viewModel.state.value.items.isEmpty())
    }

    @Test
    fun `given loaded data when item clicked then state changed`() {
        coEvery { getGridTextUseCaseMock.invoke(any()) } returns textDomainModelList
        every { itemStateMapperMock.invoke(any()) } returns itemState

        val itemId = 1

        viewModel.onAction(GridAction.LoadMore)
        viewModel.onAction(GridAction.OnItemClicked(itemId))

        viewModel.state.value.items
            .filter { it.id == itemId }
            .forEach {
                assertEquals(itemState.title, it.title)
                assertFalse(it.isEditable)
                assertTrue(it.isSelected)
            }
    }

    @Test
    fun `given loaded data when item double clicked then state changed`() {
        coEvery { getGridTextUseCaseMock.invoke(any()) } returns textDomainModelList
        every { itemStateMapperMock.invoke(any()) } returns itemState

        val itemId = 1

        viewModel.onAction(GridAction.LoadMore)
        viewModel.onAction(GridAction.OnItemDoubleClicked(itemId))

        viewModel.state.value.items
            .filter { it.id == itemId }
            .forEach {
                assertEquals(itemState.title, it.title)
                assertFalse(it.isSelected)
                assertTrue(it.isEditable)
            }
    }

    @Test
    fun `given loaded data when item edited then state changed`() {
        coEvery { getGridTextUseCaseMock.invoke(any()) } returns textDomainModelList
        every { itemStateMapperMock.invoke(any()) } returns itemState

        val itemId = 1
        val newText = "new text"

        viewModel.onAction(GridAction.LoadMore)
        viewModel.onAction(GridAction.OnTitleChanged(itemId, newText))

        viewModel.state.value.items
            .filter { it.id == itemId }
            .forEach {
                assertEquals(newText, it.title)
                assertFalse(it.isSelected)
                assertFalse(it.isEditable)
            }
    }
}
