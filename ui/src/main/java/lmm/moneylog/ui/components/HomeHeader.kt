package lmm.moneylog.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.MoneylogTheme

@Composable
fun HomeHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeHeaderPreview() {
    MoneylogTheme {
        HomeHeader(
            text = "October 2023",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
