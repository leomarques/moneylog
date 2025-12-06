package lmm.moneylog.ui.features.balancecard.view.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.features.balancecard.view.components.Amount
import lmm.moneylog.ui.features.balancecard.view.components.Balance
import lmm.moneylog.ui.features.balancecard.view.components.BalanceCardContent
import lmm.moneylog.ui.features.balancecard.view.components.LockButton
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.outcome

const val BALANCE_CARD_ALL = "all"
const val BALANCE_CARD_INCOME = "income"
const val BALANCE_CARD_OUTCOME = "outcome"

@Composable
fun BalanceCardLayout(
    total: String,
    credit: String,
    debt: String,
    month: String,
    hideValues: Boolean,
    onHideClick: () -> Unit,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BalanceCardContent(modifier) {
        Balance(
            value = total,
            onClick = { onClick(BALANCE_CARD_ALL) },
            hideValues = hideValues,
            month = month
        )

        LockButton(
            onHideClick = onHideClick,
            isHidden = hideValues
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Amount(
                modifier = Modifier.weight(0.5f),
                title = stringResource(R.string.common_incomes),
                value = credit,
                color = income,
                hideValue = hideValues,
                onClick = { onClick(BALANCE_CARD_INCOME) }
            )

            Amount(
                modifier = Modifier.weight(0.5f),
                title = stringResource(R.string.common_outcome),
                value = debt,
                color = outcome,
                hideValue = hideValues,
                onClick = { onClick(BALANCE_CARD_OUTCOME) }
            )
        }
    }
}

@Preview
@Composable
private fun BalanceCardPreview() {
    AppTheme {
        BalanceCardLayout(
            total = "R$9999999999999999999999999999999999999999999999999999999999999999999,00,00",
            credit = "R$999999999999999999999999999999999999999999999999999999999999999,00,00,00",
            debt = "R$99999999999999999999999999999999999999999999999999999999999999999999,00,00",
            onClick = {},
            hideValues = false,
            onHideClick = {},
            month = "Outubro"
        )
    }
}
