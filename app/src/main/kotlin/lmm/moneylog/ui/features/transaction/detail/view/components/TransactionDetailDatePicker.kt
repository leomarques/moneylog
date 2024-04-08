package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailDatePicker(
    onConfirm: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(
        modifier = modifier,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis ?: 0)
                    onDismiss()
                },
                enabled = confirmEnabled.value
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        onDismissRequest = onDismiss
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview
@Composable
private fun TransactionDetailDatePickerPreview() {
    TransactionDetailDatePicker(
        onConfirm = {},
        onDismiss = {}
    )
}
