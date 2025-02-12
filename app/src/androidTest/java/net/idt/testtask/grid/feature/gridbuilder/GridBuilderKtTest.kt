package net.idt.testtask.grid.feature.gridbuilder

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import io.mockk.mockk
import io.mockk.verify
import net.idt.testtask.grid.R
import net.idt.testtask.grid.feature.gridbuilder.GridBuilderAction.ApplySettings
import net.idt.testtask.grid.utils.setContentWithTheme
import org.junit.Rule
import org.junit.Test

private const val COL_NUMBER_TEXT = "6"
private const val ROW_NUMBER_TEXT = "1000"

class GridBuilderKtTest {
    @get:Rule val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val onActionMockk = mockk<(GridBuilderAction) -> Unit>(relaxed = true)

    private val columnsNumberLabelText by lazy { getResString(R.string.columns_number_text_label) }
    private val rowsNumberLabelText by lazy { getResString(R.string.rows_number_text_label) }
    private val columnsNumberErrorLabelText by lazy { getResString(R.string.columns_number_error_text_label) }
    private val rowsNumberErrorLabelText by lazy { getResString(R.string.rows_number_error_text_label) }
    private val applyButtonLabelText by lazy { getResString(R.string.apply_button_text) }

    @Test
    fun whenGridScreenStartedThenAllNodesVisible() {
        startScreen(hasErrors = false)

        with(composeTestRule) {
            onNodeWithText(columnsNumberLabelText).assertIsDisplayed()
            onNodeWithText(rowsNumberLabelText).assertIsDisplayed()
            onNodeWithText(applyButtonLabelText).assertIsDisplayed()
        }
    }

    @Test
    fun whenGridScreenHasErrorsThenAllNodesVisible() {
        startScreen(hasErrors = true)

        with(composeTestRule) {
            onNodeWithText(columnsNumberLabelText).assertIsDisplayed()
            onNodeWithText(rowsNumberLabelText).assertIsDisplayed()
            onNodeWithText(columnsNumberErrorLabelText).assertIsDisplayed()
            onNodeWithText(rowsNumberErrorLabelText).assertIsDisplayed()
            onNodeWithText(applyButtonLabelText).assertIsDisplayed()
        }
    }

    @Test
    fun whenApplyButtonClickedThenActionCallbackInvoked() {
        startScreen(hasErrors = false)

        performTextInputForAllTextFields()
        clickApplyButton()

        verify(exactly = 1) {
            onActionMockk.invoke(ApplySettings(COL_NUMBER_TEXT, ROW_NUMBER_TEXT))
        }
    }

    @Test
    fun whenErrorsShownApplyButtonClickedThenActionCallbackInvoked() {
        startScreen(hasErrors = true)

        performTextInputForAllTextFields()
        clickApplyButton()

        verify(exactly = 1) {
            onActionMockk.invoke(ApplySettings(COL_NUMBER_TEXT, ROW_NUMBER_TEXT))
        }
    }

    @Test
    fun whenDoneKeyClickedThenActionCallbackInvoked() {
        startScreen(hasErrors = false)

        performTextInputForAllTextFields()
        clickDoneImeButton()

        verify(exactly = 1) {
            onActionMockk.invoke(ApplySettings(COL_NUMBER_TEXT, ROW_NUMBER_TEXT))
        }
    }

    @Test
    fun whenErrorsShownAndDoneKeyClickedThenActionCallbackInvoked() {
        startScreen(hasErrors = true)

        performTextInputForAllTextFields()
        clickDoneImeButton()

        verify(exactly = 1) {
            onActionMockk.invoke(ApplySettings(COL_NUMBER_TEXT, ROW_NUMBER_TEXT))
        }
    }

    private fun performTextInputForAllTextFields() {
        with(composeTestRule) {
            onNodeWithText(columnsNumberLabelText).performTextInput(COL_NUMBER_TEXT)
            onNodeWithText(rowsNumberLabelText).performTextInput(ROW_NUMBER_TEXT)
        }
    }

    private fun clickDoneImeButton() {
        composeTestRule.onNodeWithText(rowsNumberLabelText).performImeAction()
    }

    private fun clickApplyButton() {
        composeTestRule.onNodeWithText(rowsNumberLabelText).performImeAction()
    }

    private fun getResString(@StringRes resId: Int): String =
        composeTestRule.activity.getString(resId)

    private fun startScreen(hasErrors: Boolean) {
        composeTestRule.setContentWithTheme {
            GridBuilderScreen(
                modifier = Modifier.fillMaxSize(),
                state = GridBuilderState(
                    hasColNumberError = hasErrors,
                    hasRowNumberError = hasErrors
                ),
                onAction = onActionMockk
            )
        }
    }
}