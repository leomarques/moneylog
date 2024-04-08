package lmm.moneylog.ui.features.balancecard.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.theme.Size

@Composable
fun Balance(
    modifier: Modifier = Modifier,
    value: String,
    month: String,
    hideValues: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize),
            text = month,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.balancecard_total),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.titleSmall
        )

        Text(
            text = if (hideValues) "****" else value,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun BalancePreview() {
    Surface {
        Balance(
            value = "R$ 1.000,00",
            month = "Janeiro",
            hideValues = false,
            onClick = {}
        )
    }
}
