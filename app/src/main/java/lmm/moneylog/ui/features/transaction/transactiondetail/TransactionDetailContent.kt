package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import lmm.moneylog.R
import lmm.moneylog.ui.components.AccCatClickField
import lmm.moneylog.ui.components.BottomSheetContent
import lmm.moneylog.ui.components.ClickTextField
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.features.transaction.transactiondetail.components.DeleteTransactionConfirmDialog
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionDetailDatePicker
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionRadioGroup
import lmm.moneylog.ui.theme.SpaceSize
import lmm.moneylog.ui.theme.income

@OptIn(ExperimentalMaterial3Api::class)
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
            ModalBottomSheet(
                onDismissRequest = {
                    showAccountPicker = false
                }
            ) {
                BottomSheetContent(
                    list = model.accounts.map { it.name to Color(it.color.toULong()) },
                    onConfirm = { index ->
                        onAccountPicked(index)
                    }
                ) {
                    showAccountPicker = false
                }
            }
        }
        if (showCategoryPicker) {
            ModalBottomSheet(
                onDismissRequest = {
                    showAccountPicker = false
                }
            ) {
                BottomSheetContent(
                    list = filteredCategories.map { it.name to Color(it.color.toULong()) },
                    onConfirm = { index ->
                        onCategoryPicked(index)
                    }
                ) {
                    showCategoryPicker = false
                }
            }
        }

        if (showDeleteConfirmDialog) {
            DeleteTransactionConfirmDialog(
                onConfirm = onDeleteConfirm,
                onDismiss = onDeleteDismiss
            )
        }

        StateTextField(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            title = stringResource(R.string.detailtransaction_value),
            keyboardType = KeyboardType.Number,
            valueState = model.value,
            getFocus = !model.isEdit,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_attach_money_24),
                    contentDescription = stringResource(R.string.detailtransaction_value_icon_desc),
                    tint = if (model.isIncome.value) income else Color.Red
                )
            }
        )

        TransactionRadioGroup(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            model.isIncome
        ) {
            onIsIncomeSelected()
        }

        ClickTextField(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            value = model.displayDate,
            title = stringResource(R.string.detailtransaction_date),
            onClick = {
                showDatePicker = true
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.detailtransaction_date_icon_desc)
                )
            }
        )

        StateTextField(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            title = stringResource(R.string.detailtransaction_description),
            keyboardType = KeyboardType.Text,
            valueState = model.description,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = stringResource(R.string.detailtransaction_description_icon_desc)
                )
            }
        )

        AccCatClickField(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            title = stringResource(R.string.detailtransaction_account),
            value = model.displayAccount,
            enabled = model.accounts.isNotEmpty(),
            onClick = {
                showAccountPicker = true
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_account_balance_24),
                    contentDescription = stringResource(R.string.detailtransaction_account_icon_desc),
                    tint = model.displayAccountColor
                )
            }
        )

        AccCatClickField(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            title = stringResource(R.string.detailtransaction_category),
            value = model.displayCategory,
            enabled = filteredCategories.isNotEmpty(),
            onClick = {
                showCategoryPicker = true
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_category_24),
                    contentDescription = stringResource(R.string.detailtransaction_category_icon_desc),
                    tint = model.displayCategoryColor
                )
            }
        )
    }
}
