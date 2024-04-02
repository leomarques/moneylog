package lmm.moneylog.ui.components.textfields

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
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun ColorTextField(
    color: Color,
    onClick: (() -> Unit),
    leadingIcon: (@Composable () -> Unit)? = null
) {
    Box(contentAlignment = Alignment.CenterStart) {
        OutlinedTextField(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth(),
            value = stringResource(R.string.color),
            readOnly = true,
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
            onValueChange = { },
            leadingIcon = leadingIcon
        )

        MyCircle(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = Size.DefaultSpaceSize),
            color = color,
            size = Size.SmallCircleSize
        )
    }
}

@Preview
@Composable
fun ColorTextFieldPreview() {
    ColorTextField(
        color = outcome,
        onClick = {}
    )
}
