package lmm.moneylog.ui.features.transaction.transactiondetail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import lmm.moneylog.ui.features.transaction.transactiondetail.TransactionDetailModel

@Composable
fun TransactionRadioGroup(
    model: TransactionDetailModel,
    onTypeOfValueSelected: (Boolean) -> Unit
) {
    Row {
        Row(
            Modifier
                .selectable(
                    selected = model.isIncome.value,
                    onClick = { onTypeOfValueSelected(true) }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = model.isIncome.value,
                onClick = { onTypeOfValueSelected(true) }
            )
            Text(
                text = stringResource(R.string.detailtransaction_income)
            )
        }

        Row(
            Modifier.selectable(
                selected = !model.isIncome.value,
                onClick = { onTypeOfValueSelected(false) }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !model.isIncome.value,
                onClick = { onTypeOfValueSelected(false) }
            )
            Text(
                text = stringResource(R.string.detailtransaction_outcome)
            )
        }
    }
}
