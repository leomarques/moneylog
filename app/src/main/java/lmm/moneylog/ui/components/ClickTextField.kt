package lmm.moneylog.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun ClickTextField(
    modifier: Modifier = Modifier,
    value: String,
    title: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        leadingIcon = leadingIcon,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = SpaceSize.SmallSpaceSize),
        value = value,
        label = { Text(text = title) },
        readOnly = true,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onClick.invoke()
                        }
                    }
                }
            },
        onValueChange = { }
    )
}
