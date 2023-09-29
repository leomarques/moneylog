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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.features.transaction.transactiondetail.components.DeleteTransactionConfirmDialog
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionDetailDatePicker
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionRadioGroup
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: TransactionDetailModel,
    onDatePicked: (Long) -> Unit,
    onTypeOfValueSelected: (Boolean) -> Unit,
    onDeleteConfirmClick: (Int) -> Unit = {}
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(model.titleResourceId))
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
                    if (model.isEdit) {
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
                    model = model,
                    onDatePicked = onDatePicked,
                    onTypeOfValueSelected = onTypeOfValueSelected,
                    onDeleteConfirm = {
                        onDeleteConfirmClick(model.id)
                    },
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onDeleteDismiss = {
                        showDeleteConfirmDialog.value = false
                    }
                )
            }
        }
    )
}

@Composable
private fun Content(
    model: TransactionDetailModel,
    onDatePicked: (Long) -> Unit,
    onTypeOfValueSelected: (Boolean) -> Unit,
    showDeleteConfirmDialog: Boolean,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        val showDatePicker = remember { mutableStateOf(false) }

        if (showDatePicker.value) {
            TransactionDetailDatePicker(
                onConfirm = {
                    onDatePicked(it)
                },
                onDismiss = {
                    showDatePicker.value = false
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

        TransactionRadioGroup(
            model = model,
            onTypeOfValueSelected = onTypeOfValueSelected
        )

        StateTextField(
            title = stringResource(R.string.detailtransaction_date),
            keyboardType = KeyboardType.Text,
            valueState = model.displayDate,
            onClick = {
                showDatePicker.value = true
            }
        )
        StateTextField(
            title = stringResource(R.string.detailtransaction_description),
            keyboardType = KeyboardType.Text,
            valueState = model.description
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun Preview() {
    TransactionDetailLayout(
        onArrowBackClick = {},
        onFabClick = {},
        model = TransactionDetailModel(
            value = mutableStateOf(""),
            isIncome = mutableStateOf(true),
            displayDate = mutableStateOf(""),
            description = mutableStateOf(""),
            date = DomainTime(0, 0, 0),
            isEdit = false,
            id = 0,
            titleResourceId = R.string.detailtransaction_topbar_title_add
        ),
        onDatePicked = {},
        onTypeOfValueSelected = {},
        onDeleteConfirmClick = {}
    )
}
