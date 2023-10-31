package lmm.moneylog.ui.components.textfields

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import lmm.moneylog.ui.components.OnLifecycleEvent

@Composable
fun StateTextField(
    modifier: Modifier = Modifier,
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>,
    getFocus: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .focusRequester(focusRequester),
        leadingIcon = leadingIcon,
        value = valueState.value,
        label = { Text(text = title) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        onValueChange = { value -> valueState.value = value }
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

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun StateTextFieldPreview() {
    StateTextField(
        title = "Title",
        keyboardType = KeyboardType.Text,
        valueState = mutableStateOf("Value")
    )
}
