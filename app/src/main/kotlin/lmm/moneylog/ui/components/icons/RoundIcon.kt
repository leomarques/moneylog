package lmm.moneylog.ui.components.icons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R

@Composable
private fun RoundIcon(
    iconRes: Int,
    backgroundColor: Color,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = backgroundColor,
        modifier = modifier.size(40.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun BalanceRoundIcon(
    modifier: Modifier = Modifier
) {
    RoundIcon(
        iconRes = lmm.moneylog.ui.R.drawable.balance_24px,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentDescription = stringResource(R.string.accessibility_adjust_balance),
        modifier = modifier
    )
}

@Composable
fun MoneyRoundIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    RoundIcon(
        iconRes = R.drawable.outline_attach_money_24,
        backgroundColor =
            if (enabled) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
        contentDescription = stringResource(R.string.accessibility_pay),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun BalanceRoundIconPreview() {
    BalanceRoundIcon()
}

@Preview(showBackground = true)
@Composable
private fun MoneyRoundIconPreview() {
    MoneyRoundIcon(enabled = true)
}

@Preview(showBackground = true)
@Composable
private fun MoneyRoundIconDisabledPreview() {
    MoneyRoundIcon(enabled = false)
}
