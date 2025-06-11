package lmm.moneylog.ui.features.invoice.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import lmm.moneylog.R
import lmm.moneylog.ui.components.MonthSelector
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun CardInfo(
    cardName: String,
    isInvoicePaid: Boolean,
    totalValue: String,
    monthName: String,
    onPayClick: () -> Unit,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = cardName,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize)
        )

        MonthSelector(
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize),
            onPreviousMonthClick = onPreviousMonthClick,
            onNextMonthClick = onNextMonthClick,
            monthName = monthName
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            InvoiceTotal(totalValue = totalValue)

            Button(
                enabled = !isInvoicePaid,
                onClick = onPayClick
            ) {
                Text(
                    if (!isInvoicePaid) {
                        stringResource(R.string.pay)
                    } else {
                        stringResource(R.string.invoice_paid)
                    }
                )
            }
        }
    }
}

@Composable
private fun InvoiceTotal(totalValue: String) {
    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.invoice_total),
            color = Color.Gray
        )

        Text(
            text = totalValue,
            color = outcome
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CardInfoPreview() {
    CardInfo(
        cardName = "Visa",
        isInvoicePaid = true,
        totalValue = "R$153,50",
        monthName = "Janeiro",
        onPayClick = {},
        onPreviousMonthClick = {},
        onNextMonthClick = {}
    )
}
