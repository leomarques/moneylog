package lmm.moneylog.ui.features.creditcard.homecard.view.layout

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.creditcard.homecard.view.components.CreditHomeCardContent
import lmm.moneylog.ui.theme.MoneylogTheme

@Composable
fun CreditHomeCardLayout(
    total: String,
    hideValues: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CreditHomeCardContent(
        modifier = modifier,
        onClick = onClick,
        content = { Text(if (hideValues) "#######" else "Hello Credit Card!") }
    )
}

@Preview
@Composable
private fun CreditHomeCardLayoutPreview() {
    MoneylogTheme {
        CreditHomeCardLayout(
            total = "R$9999999999999999999999999999999999999999999999999999999999999999999,00,00",
            onClick = {},
            hideValues = false
        )
    }
}
