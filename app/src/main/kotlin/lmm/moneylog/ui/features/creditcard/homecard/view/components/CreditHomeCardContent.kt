package lmm.moneylog.ui.features.creditcard.homecard.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun CreditHomeCardContent(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Size.DefaultSpaceSize))
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .padding(Size.DefaultSpaceSize)
                .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Preview
@Composable
private fun CreditHomeCardContentPreview() {
    CreditHomeCardContent(
        content = { Text("CreditHomeCardContent") },
        onClick = {}
    )
}
