package lmm.moneylog.ui.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import lmm.moneylog.ui.components.misc.OnLifecycleEvent

@Composable
fun StateTextField(
    value: String,
    title: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    getFocus: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    testTag: String = ""
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier =
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .focusRequester(focusRequester)
            .testTag(testTag),
        value = value,
        label = { Text(text = title) },
        leadingIcon = leadingIcon,
        onValueChange = onValueChange,
        keyboardOptions =
        KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions =
        KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )

    if (getFocus) {
        OnLifecycleEvent { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    focusRequester.requestFocus()
                }

                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun StateTextFieldPreview() {
    StateTextField(
        value = "Value",
        title = "Title",
        keyboardType = KeyboardType.Text,
        onValueChange = {}
    )
}
