package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import lmm.moneylog.R
import lmm.moneylog.ui.components.AccCatClickField
import lmm.moneylog.ui.components.ClickTextField
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.components.TextPicker
import lmm.moneylog.ui.features.transaction.transactiondetail.components.DeleteTransactionConfirmDialog
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionDetailDatePicker
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionRadioGroup
import lmm.moneylog.ui.theme.SpaceSize

@Composable
fun TransactionDetailContent(
    model: TransactionDetailModel,
    showDeleteConfirmDialog: Boolean,
    onDatePicked: (Long) -> Unit,
    onAccountPicked: (Int) -> Unit,
    onCategoryPicked: (Int) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onIsIncomeSelected: () -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        var showDatePicker by remember { mutableStateOf(false) }
        var showAccountPicker by remember { mutableStateOf(false) }
        var showCategoryPicker by remember { mutableStateOf(false) }

        val filteredCategories = model.categories.filter { it.isIncome == model.isIncome.value }

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
                list = model.accounts.map { it.name },
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
                list = filteredCategories.map { it.name },
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
            valueState = model.value,
            getFocus = !model.isEdit
        )

        TransactionRadioGroup(model.isIncome) {
            onIsIncomeSelected()
        }

        ClickTextField(
            title = stringResource(R.string.detailtransaction_date),
            value = model.displayDate,
            onClick = {
                showDatePicker = true
            }
        )

        StateTextField(
            title = stringResource(R.string.detailtransaction_description),
            keyboardType = KeyboardType.Text,
            valueState = model.description
        )

        AccCatClickField(
            title = stringResource(R.string.detailtransaction_account),
            value = model.displayAccount,
            enabled = model.accounts.isNotEmpty(),
            color = model.displayAccountColor
        ) {
            showAccountPicker = true
        }

        AccCatClickField(
            title = stringResource(R.string.detailtransaction_category),
            value = model.displayCategory,
            enabled = filteredCategories.isNotEmpty(),
            color = model.displayCategoryColor
        ) {
            showCategoryPicker = true
        }
    }
}
