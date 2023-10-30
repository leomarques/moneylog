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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.ColorClickField
import lmm.moneylog.ui.components.ColorPicker
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TransactionRadioGroup
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit = {},
    isEdit: Boolean,
    valueState: MutableState<String>,
    isIncome: MutableState<Boolean>,
    onColorPicked: (Color) -> Unit,
    color: Color,
    showFab: Boolean
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
            if (showFab) {
                MyFab(
                    onClick = onFabClick,
                    icon = Icons.Default.Check
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                Content(
                    color = color,
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onDeleteConfirm = {
                        showDeleteConfirmDialog.value = false
                        onDeleteConfirmClick()
                    },
                    onDeleteDismiss = {
                        showDeleteConfirmDialog.value = false
                    },
                    isEdit = isEdit,
                    valueState = valueState,
                    isIncome = isIncome,
                    onColorPicked = onColorPicked
                )
            }
        }
    )
}

@Composable
private fun Content(
    color: Color,
    onColorPicked: (Color) -> Unit,
    showDeleteConfirmDialog: Boolean,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    isEdit: Boolean,
    valueState: MutableState<String>,
    isIncome: MutableState<Boolean>
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        var showColorsDialog by remember { mutableStateOf(false) }

        if (showDeleteConfirmDialog) {
            DeleteCategoryConfirmDialog(
                onConfirm = onDeleteConfirm,
                onDismiss = onDeleteDismiss
            )
        }

        if (showColorsDialog) {
            ColorPicker(
                onConfirm = {
                    onColorPicked(it)
                    showColorsDialog = false
                },
                onDismiss = {
                    showColorsDialog = false
                }
            )
        }

        StateTextField(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            title = stringResource(R.string.name),
            keyboardType = KeyboardType.Text,
            valueState = valueState,
            getFocus = !isEdit
        )

        TransactionRadioGroup(
            modifier = Modifier.padding(bottom = SpaceSize.DefaultSpaceSize),
            isIncome = isIncome
        ) {}

        ColorClickField(
            color = color,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_brush_24),
                    contentDescription = stringResource(R.string.detailtransaction_color_icon_desc)
                )
            },
            onClick = {
                showColorsDialog = true
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
        isIncome = mutableStateOf(true),
        onColorPicked = {},
        color = Color.Gray,
        showFab = true
    )
}
