package lmm.moneylog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun ColorClickField(
    color: Color,
    enabled: Boolean = true,
    onClick: (() -> Unit),
    leadingIcon: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        OutlinedTextField(
            leadingIcon = leadingIcon,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            value = stringResource(R.string.color),
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

        MyCircle(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = SpaceSize.DefaultSpaceSize),
            color = color,
            size = 30.dp
        )
    }
}

@Preview
@Composable
fun ClickFieldPreview() {
    ColorClickField(color = Color.Red, onClick = {})
}
