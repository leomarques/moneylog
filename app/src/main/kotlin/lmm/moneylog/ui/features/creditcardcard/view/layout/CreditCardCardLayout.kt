package lmm.moneylog.ui.features.creditcardcard.view.layout

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.creditcardcard.view.components.CreditCardCardContent
import lmm.moneylog.ui.theme.MoneylogTheme

@Composable
fun CreditCardCardLayout(
    total: String,
    hideValues: Boolean,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    CreditCardCardContent(modifier) {
        Text("Hello Credit Card!")
    }
}

@Preview
@Composable
private fun BalanceCardPreview() {
    MoneylogTheme {
        CreditCardCardLayout(
            total = "R$9999999999999999999999999999999999999999999999999999999999999999999,00,00",
            onClick = {},
            hideValues = false
        )
    }
}
