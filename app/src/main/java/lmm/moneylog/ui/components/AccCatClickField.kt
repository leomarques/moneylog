package lmm.moneylog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun AccCatClickField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    Box(contentAlignment = Alignment.CenterStart) {
        OutlinedTextField(
            leadingIcon = leadingIcon,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = SpaceSize.SmallSpaceSize)
                .background(MaterialTheme.colorScheme.surface),
            value = value,
            label = { Text(title) },
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
            onValueChange = {}
        )
    }
}

@Preview
@Composable
fun AccCatClickFieldPreview() {
    AccCatClickField(
        title = "",
        value = "Nubank",
        onClick = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
        }
    )
}
