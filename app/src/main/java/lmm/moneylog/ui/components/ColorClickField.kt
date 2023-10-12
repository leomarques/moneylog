package lmm.moneylog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun ColorClickField(
    color: Color,
    enabled: Boolean = true,
    onClick: (() -> Unit)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(SpaceSize.ListItemHeight),
        contentAlignment = Alignment.CenterStart
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            value = "",
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

        Row(
            modifier = Modifier
                .padding(horizontal = SpaceSize.DefaultSpaceSize)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.color_field))
            MyCircle(color = color)
        }
    }
}

@Preview
@Composable
fun ClickFieldPreview() {
    ColorClickField(Color.Red) {}
}
