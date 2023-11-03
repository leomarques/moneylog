package lmm.moneylog.ui.features.account.list.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun ListItemBalance(
    balance: String,
    color: Color
) {
    Row {
        Text(
            text = stringResource(R.string.list_account_balance),
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = balance,
            style = MaterialTheme.typography.bodyMedium,
            color = color
        )
    }
}

@Preview
@Composable
fun ListItemBalancePreview() {
    ListItemBalance(
        balance = "R$ 1.000,00",
        color = Color.Red
    )
}
