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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyFab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailLayout(
    model: TransactionDetailModel,
    onArrowBackClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit,
    onFabClick: () -> Unit,
    onDatePicked: (Long) -> Unit,
    onAccountPicked: (Int) -> Unit,
    onCategoryPicked: (Int) -> Unit,
    onIsIncomeSelected: () -> Unit
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(model.titleResourceId))
                },
                navigationIcon = {
                    val focusManager = LocalFocusManager.current
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            onArrowBackClick()
                        }
                    ) {
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
            if (model.showFab) {
                MyFab(
                    onClick = onFabClick,
                    icon = Icons.Default.Check
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                TransactionDetailContent(
                    model = model,
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onDatePicked = onDatePicked,
                    onAccountPicked = onAccountPicked,
                    onCategoryPicked = onCategoryPicked,
                    onDeleteConfirm = {
                        showDeleteConfirmDialog.value = false
                        onDeleteConfirmClick()
                    },
                    onDeleteDismiss = { showDeleteConfirmDialog.value = false },
                    onIsIncomeSelected = onIsIncomeSelected
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
        model = TransactionDetailModel(),
        onArrowBackClick = {},
        onDeleteConfirmClick = {},
        onFabClick = {},
        onDatePicked = {},
        onAccountPicked = {},
        onCategoryPicked = {},
        onIsIncomeSelected = {}
    )
}
