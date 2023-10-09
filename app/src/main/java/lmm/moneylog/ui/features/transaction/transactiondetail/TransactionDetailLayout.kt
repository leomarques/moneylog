package lmm.moneylog.ui.features.transaction.transactiondetail

import android.annotation.SuppressLint
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyFab

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
                TransactionDetailContent(
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

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun TransactionDetailLayoutPreview() {
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
