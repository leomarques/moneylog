package lmm.moneylog.ui.features.addtransaction

import android.annotation.SuppressLint
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun AddTransactionDatePicker(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis ?: 0)
                    onDismiss()
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
