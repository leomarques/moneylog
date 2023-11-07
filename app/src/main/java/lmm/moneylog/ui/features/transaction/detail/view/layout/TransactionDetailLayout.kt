package lmm.moneylog.ui.features.transaction.detail.view.layout

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
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailLayout(
    uiState: TransactionDetailUIState,
    onArrowBackClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit,
    onFabClick: () -> Unit,
    onDatePick: (Long) -> Unit,
    onAccountPick: (Int) -> Unit,
    onCategoryPick: (Int) -> Unit,
    onIsIncomeSelect: () -> Unit
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(uiState.titleResourceId))
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
                            contentDescription = stringResource(R.string.arrowback_desc)
                        )
                    }
                },
                actions = {
                    if (uiState.isEdit) {
                        IconButton(
                            onClick = { showDeleteConfirmDialog.value = true },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.delete)
                                )
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState.showFab) {
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
                    model = uiState,
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onIsIncomeSelect = onIsIncomeSelect,
                    onDatePicked = onDatePick,
                    onAccountPicked = onAccountPick,
                    onCategoryPicked = onCategoryPick,
                    onDeleteDismiss = { showDeleteConfirmDialog.value = false },
                    onDeleteConfirm = {
                        showDeleteConfirmDialog.value = false
                        onDeleteConfirmClick()
                    }
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
        uiState = TransactionDetailUIState(),
        onArrowBackClick = {},
        onDeleteConfirmClick = {},
        onFabClick = {},
        onDatePick = {},
        onAccountPick = {},
        onCategoryPick = {},
        onIsIncomeSelect = {}
    )
}
