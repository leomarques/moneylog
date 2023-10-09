package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import lmm.moneylog.R
import lmm.moneylog.ui.components.ClickTextField
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.components.TextPicker
import lmm.moneylog.ui.features.transaction.transactiondetail.components.DeleteTransactionConfirmDialog
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionDetailDatePicker
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionRadioGroup
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun TransactionDetailContent(
    valueField: MutableState<String>,
    descriptionField: MutableState<String>,
    isIncomeField: MutableState<Boolean>,
    displayDate: String,
    displayAccount: String,
    displayCategory: String,
    accounts: List<String>,
    categories: List<String>,
    isEdit: Boolean,
    showDeleteConfirmDialog: Boolean,
    onDatePicked: (Long) -> Unit,
    onAccountPicked: (Int) -> Unit,
    onCategoryPicked: (Int) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        var showDatePicker by remember { mutableStateOf(false) }
        var showAccountPicker by remember { mutableStateOf(false) }
        var showCategoryPicker by remember { mutableStateOf(false) }

        if (showDatePicker) {
            TransactionDetailDatePicker(
                onConfirm = {
                    onDatePicked(it)
                },
                onDismiss = {
                    showDatePicker = false
                }
            )
        }
        if (showAccountPicker) {
            TextPicker(
                list = accounts,
                onConfirm = { index ->
                    onAccountPicked(index)
                },
                onDismiss = {
                    showAccountPicker = false
                }
            )
        }
        if (showCategoryPicker) {
            TextPicker(
                list = categories,
                onConfirm = { index ->
                    onCategoryPicked(index)
                },
                onDismiss = {
                    showCategoryPicker = false
                }
            )
        }

        if (showDeleteConfirmDialog) {
            DeleteTransactionConfirmDialog(
                onConfirm = onDeleteConfirm,
                onDismiss = onDeleteDismiss
            )
        }

        StateTextField(
            title = stringResource(R.string.detailtransaction_value),
            keyboardType = KeyboardType.Number,
            valueState = valueField,
            getFocus = !isEdit
        )

        TransactionRadioGroup(isIncomeField)

        ClickTextField(
            title = stringResource(R.string.detailtransaction_date),
            value = displayDate,
            onClick = {
                showDatePicker = true
            }
        )

        StateTextField(
            title = stringResource(R.string.detailtransaction_description),
            keyboardType = KeyboardType.Text,
            valueState = descriptionField
        )

        ClickTextField(
            title = stringResource(R.string.detailtransaction_account),
            value = displayAccount,
            enabled = accounts.isNotEmpty(),
            onClick = {
                showAccountPicker = true
            }
        )

        ClickTextField(
            title = stringResource(R.string.detailtransaction_category),
            value = displayCategory,
            enabled = categories.isNotEmpty(),
            onClick = {
                showCategoryPicker = true
            }
        )
    }
}
