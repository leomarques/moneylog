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
    modifier: Modifier = Modifier,
    isIncome: MutableState<Boolean>,
    onValueChange: (Boolean) -> Unit
) {
    Row(modifier = modifier) {
        Row(
            Modifier
                .selectable(
                    selected = isIncome.value,
                    onClick = {
                        isIncome.value = true
                        onValueChange(true)
                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isIncome.value,
                onClick = {
                    isIncome.value = true
                    onValueChange(true)
                }
            )
            Text(
                text = stringResource(R.string.income)
            )
        }

        Row(
            Modifier.selectable(
                selected = !isIncome.value,
                onClick = {
                    isIncome.value = false
                    onValueChange(false)
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !isIncome.value,
                onClick = {
                    isIncome.value = false
                    onValueChange(false)
                }
            )
            Text(
                text = stringResource(R.string.outcome)
            )
        }
    }
}
