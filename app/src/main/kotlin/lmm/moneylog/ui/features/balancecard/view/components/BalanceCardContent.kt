package lmm.moneylog.ui.features.balancecard.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.Size

@Composable
fun BalanceCardContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(
                    bottomStart = Size.DefaultSpaceSize,
                    bottomEnd = Size.DefaultSpaceSize
                ))
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .padding(Size.DefaultSpaceSize),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Preview
@Composable
private fun BalanceCardContentPreview() {
    BalanceCardContent {
        Text("BalanceCardContent")
    }
}
