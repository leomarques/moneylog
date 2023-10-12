package lmm.moneylog.ui.features.account.accountdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.ColorClickField
import lmm.moneylog.ui.components.ColorPicker
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.features.account.archive.ArchiveAccountConfirmDialog
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    isEdit: Boolean,
    valueState: MutableState<String>,
    onArchiveIconClick: () -> Unit = {},
    onColorPicked: (Color) -> Unit,
    color: Color
) {
    val showArchiveConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            if (isEdit) {
                                R.string.topbar_title_account_edit
                            } else {
                                R.string.topbar_title_account_add
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
                            onClick = { showArchiveConfirmDialog.value = true },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Build,
                                    contentDescription = stringResource(R.string.archive_desc)
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
                    color = color,
                    showArchiveConfirmDialog = showArchiveConfirmDialog.value,
                    onArchiveConfirm = onArchiveIconClick,
                    onArchiveDismiss = {
                        showArchiveConfirmDialog.value = false
                    },
                    isEdit = isEdit,
                    valueState = valueState,
                    onColorPicked = onColorPicked
                )
            }
        }
    )
}

@Composable
private fun Content(
    showArchiveConfirmDialog: Boolean,
    onArchiveConfirm: () -> Unit,
    onArchiveDismiss: () -> Unit,
    onColorPicked: (Color) -> Unit,
    isEdit: Boolean,
    valueState: MutableState<String>,
    color: Color
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        var showColorsDialog by remember { mutableStateOf(false) }

        if (showArchiveConfirmDialog) {
            ArchiveAccountConfirmDialog(
                onConfirm = onArchiveConfirm,
                onDismiss = onArchiveDismiss
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
            title = stringResource(R.string.name),
            keyboardType = KeyboardType.Text,
            valueState = valueState,
            getFocus = !isEdit
        )

        ColorClickField(color = color) {
            showColorsDialog = true
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun AccountDetailLayoutPreview() {
    AccountDetailLayout(
        onArrowBackClick = {},
        onFabClick = {},
        isEdit = false,
        valueState = mutableStateOf(""),
        onArchiveIconClick = {},
        onColorPicked = {},
        color = Color.Gray
    )
}
