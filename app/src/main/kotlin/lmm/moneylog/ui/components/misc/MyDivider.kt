package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size

@Composable
fun MyDivider(
    modifier: Modifier = Modifier,
    color: Color = DividerDefaults.color
) {
    HorizontalDivider(
        modifier.padding(horizontal = Size.DefaultSpaceSize),
        color = color
    )
}

@Preview(showBackground = true)
@Composable
private fun MyDividerPreview() {
    MyDivider()
}
