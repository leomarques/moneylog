package lmm.moneylog.ui.features.category.categorydetail

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
import lmm.moneylog.ui.components.TextPicker
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit = {},
    isEdit: Boolean,
    valueState: MutableState<String>,
    onIsIncomeSelected: (Boolean) -> Unit
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            if (isEdit) {
                                R.string.topbar_title_category_edit
                            } else {
                                R.string.topbar_title_category_edit
                            }
                        )
                    )
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
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onDeleteConfirm = onDeleteConfirmClick,
                    onDeleteDismiss = {
                        showDeleteConfirmDialog.value = false
                    },
                    isEdit = isEdit,
                    valueState = valueState,
                    onIsIncomeSelected = onIsIncomeSelected
                )
            }
        }
    )
}

@Composable
private fun Content(
    showDeleteConfirmDialog: Boolean,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    isEdit: Boolean,
    valueState: MutableState<String>,
    onIsIncomeSelected: (Boolean) -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        var showIsIncomePicker by remember { mutableStateOf(false) }
        var displayIsIncome by remember { mutableStateOf("") }

        if (showDeleteConfirmDialog) {
            DeleteCategoryConfirmDialog(
                onConfirm = onDeleteConfirm,
                onDismiss = onDeleteDismiss
            )
        }

        if (showIsIncomePicker) {
            val list = listOf(
                stringResource(id = R.string.balancecard_income),
                stringResource(id = R.string.balancecard_outcome)
            )
            TextPicker(
                list = list,
                onConfirm = { index ->
                    onIsIncomeSelected(index == 0)
                    displayIsIncome = list[index]
                },
                onDismiss = {
                    showIsIncomePicker = false
                }
            )
        }

        StateTextField(
            title = stringResource(R.string.name),
            keyboardType = KeyboardType.Text,
            valueState = valueState,
            getFocus = !isEdit
        )

        ClickTextField(
            title = stringResource(R.string.type),
            value = displayIsIncome,
            onClick = {
                showIsIncomePicker = true
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun CategoryDetailLayoutPreview() {
    CategoryDetailLayout(
        onArrowBackClick = {},
        onFabClick = {},
        onDeleteConfirmClick = {},
        isEdit = false,
        valueState = mutableStateOf(""),
        onIsIncomeSelected = {}
    )
}
