package lmm.moneylog.ui.components.textfields

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyCircle
import lmm.moneylog.ui.theme.Size

@Composable
fun ColorTextField(
    color: Color,
    onClick: (() -> Unit),
    leadingIcon: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        ClickTextField(
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize),
            value = stringResource(R.string.color),
            onClick = onClick,
            leadingIcon = leadingIcon
        )

        MyCircle(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(Size.DefaultSpaceSize),
            color = color,
            size = Size.SmallCircleSize
        )
    }
}

@Preview
@Composable
fun ColorTextFieldPreview() {
    ColorTextField(
        color = Color.Red,
        onClick = {}
    )
}
