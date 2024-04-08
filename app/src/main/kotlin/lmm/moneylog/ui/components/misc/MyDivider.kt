package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size

@Composable
fun MyDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier.padding(horizontal = Size.DefaultSpaceSize))
}

@Preview(showBackground = true)
@Composable
private fun MyDividerPreview() {
    MyDivider()
}
