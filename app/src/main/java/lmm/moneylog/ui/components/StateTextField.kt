package lmm.moneylog.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.Lifecycle
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun StateTextField(
    modifier: Modifier = Modifier,
    title: String,
    keyboardType: KeyboardType,
    valueState: MutableState<String>,
    onClick: (() -> Unit)? = null,
    getFocus: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        leadingIcon = leadingIcon,
        value = valueState.value,
        label = { Text(text = title) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        ),
        readOnly = onClick != null,
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onClick?.invoke()
                        }
                    }
                }
            },
        onValueChange = { value -> valueState.value = value },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = SpaceSize.SmallSpaceSize)
            .focusRequester(focusRequester)
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
