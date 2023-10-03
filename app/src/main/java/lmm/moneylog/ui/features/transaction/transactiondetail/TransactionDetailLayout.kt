package lmm.moneylog.ui.features.transaction.transactiondetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.ClickTextField
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.features.transaction.transactiondetail.components.DeleteTransactionConfirmDialog
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TextPicker
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionDetailDatePicker
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionRadioGroup
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailLayout(
    valueField: MutableState<String>,
    isIncomeField: MutableState<Boolean>,
    descriptionField: MutableState<String>,
    displayDate: String,
    displayAccount: String,
    displayCategory: String,
    accounts: List<String>,
    categories: List<String>,
    isEdit: Boolean,
    topBarTitle: String,
    onArrowBackClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit,
    onFabClick: () -> Unit,
    onDatePicked: (Long) -> Unit,
    onAccountPicked: (Int) -> Unit,
    onCategoryPicked: (Int) -> Unit
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = topBarTitle)
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detailtransaction_arrowback_desc)
                        )
                    }
                },
                actions = {
                    if (isEdit) {
                        IconButton(
                            onClick = { showDeleteConfirmDialog.value = true },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.detailtransaction_delete_desc)
                                )
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            MyFab(
                onClick = onFabClick,
                icon = Icons.Default.Check
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                Content(
                    valueField = valueField,
                    descriptionField = descriptionField,
                    isIncomeField = isIncomeField,
                    displayDate = displayDate,
                    displayAccount = displayAccount,
                    displayCategory = displayCategory,
                    accounts = accounts,
                    categories = categories,
                    isEdit = isEdit,
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onDatePicked = onDatePicked,
                    onAccountPicked = onAccountPicked,
                    onCategoryPicked = onCategoryPicked,
                    onDeleteConfirm = { onDeleteConfirmClick() },
                    onDeleteDismiss = { showDeleteConfirmDialog.value = false }
                )
            }
        }
    )
}

@Composable
private fun Content(
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
            onClick = {
                showAccountPicker = true
            }
        )

        ClickTextField(
            title = stringResource(R.string.detailtransaction_category),
            value = displayCategory,
            onClick = {
                showCategoryPicker = true
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun TransactionDetailLayout2Preview() {
    TransactionDetailLayout(
        valueField = mutableStateOf(""),
        isIncomeField = mutableStateOf(true),
        descriptionField = mutableStateOf(""),
        displayDate = "",
        displayAccount = "",
        displayCategory = "",
        accounts = emptyList(),
        categories = emptyList(),
        isEdit = true,
        topBarTitle = stringResource(R.string.detailtransaction_topbar_title_add),
        onArrowBackClick = {},
        onDeleteConfirmClick = {},
        onFabClick = {},
        onDatePicked = {},
        onAccountPicked = {},
        onCategoryPicked = {}
    )
}
