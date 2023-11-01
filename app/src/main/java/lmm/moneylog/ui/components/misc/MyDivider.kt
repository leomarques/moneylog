package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size

@Composable
fun MyDivider() {
    Divider(Modifier.padding(horizontal = Size.DefaultSpaceSize))
}

@Preview
@Composable
fun MyDividerPreview() {
    MyDivider()
}
