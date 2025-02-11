package net.idt.testtask.grid.feature.gridbuilder

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType.Companion.Number
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import net.idt.testtask.grid.R
import net.idt.testtask.grid.feature.gridbuilder.GridBuilderAction.ApplySettings
import net.idt.testtask.grid.ui.component.TextButton
import net.idt.testtask.grid.ui.theme.IDTGridTheme

@Composable
internal fun GridBuilder(
    modifier: Modifier = Modifier,
    state: GridBuilderState,
    onAction: (GridBuilderAction) -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center
    ) {
        var columnsNumberTextValue by rememberSaveable { mutableStateOf("") }
        GridSettingsTextField(
            value = columnsNumberTextValue,
            hasError = state.hasColNumberError,
            labelText = stringResource(R.string.columns_number_text_label),
            errorText = stringResource(R.string.columns_number_error_text_label),
            imeAction = ImeAction.Next,
            onValueChange = { columnsNumberTextValue = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        val keyboardController = LocalSoftwareKeyboardController.current
        var rowsNumberTextValue by rememberSaveable { mutableStateOf("") }
        GridSettingsTextField(
            value = rowsNumberTextValue,
            hasError = state.hasRowNumberError,
            labelText = stringResource(R.string.rows_number_text_label),
            errorText = stringResource(R.string.rows_number_error_text_label),
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onAction(ApplySettings(columnsNumberTextValue, rowsNumberTextValue))
                }
            ),
            onValueChange = { rowsNumberTextValue = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            titleText = stringResource(R.string.apply_button_text),
            onClick = { onAction(ApplySettings(columnsNumberTextValue, rowsNumberTextValue)) }
        )
    }
}

@Composable
private fun GridSettingsTextField(
    modifier: Modifier = Modifier,
    value: String,
    labelText: String,
    hasError: Boolean,
    errorText: String,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorText,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            keyboardType = Number,
            imeAction = imeAction
        ),
        label = { Text(text = labelText) },
    )
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240", showSystemUi = true)
@Composable
private fun GridBuilderPreview(
    @PreviewParameter(GridBuilderPreviewProvider::class) state: GridBuilderState
) {
    IDTGridTheme {
        GridBuilder (
            modifier = Modifier.fillMaxSize(),
            state = state
        )
    }
}
