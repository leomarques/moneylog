package lmm.moneylog.ui.components.textfields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ClickTextField(
    value: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        modifier =
            modifier
                .fillMaxWidth(),
        value = value,
        label = { Text(text = label) },
        readOnly = true,
        enabled = enabled,
        interactionSource =
            remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                onClick.invoke()
                            }
                        }
                    }
                },
        onValueChange = { },
        leadingIcon = leadingIcon
    )
}

@Preview(showBackground = true)
@Composable
private fun ClickTextFieldPreview() {
    ClickTextField(
        value = "Nubank",
        label = "Label",
        onClick = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
        }
    )
}
