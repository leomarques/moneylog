package lmm.moneylog.ui.features.balancecard.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.theme.income

@Composable
fun Amount(
    modifier: Modifier,
    title: String,
    value: String,
    color: Color,
    onClick: () -> Unit,
    hideValue: Boolean
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = if (hideValue) "****" else value,
            color = color,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun AmountPreview() {
    Surface {
        Amount(
            modifier = Modifier,
            title = "Total",
            value = "R$ 1.000,00",
            color = income,
            hideValue = false,
            onClick = {}
        )
    }
}
