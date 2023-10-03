package lmm.moneylog.ui.features.transaction.transactiondetail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R

@Composable
fun TransactionRadioGroup(
    isIncome: MutableState<Boolean>
) {
    Row {
        Row(
            Modifier
                .selectable(
                    selected = isIncome.value,
                    onClick = { isIncome.value = true }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isIncome.value,
                onClick = { isIncome.value = true }
            )
            Text(
                text = stringResource(R.string.detailtransaction_income)
            )
        }

        Row(
            Modifier.selectable(
                selected = !isIncome.value,
                onClick = { isIncome.value = false }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !isIncome.value,
                onClick = { isIncome.value = false }
            )
            Text(
                text = stringResource(R.string.detailtransaction_outcome)
            )
        }
    }
}