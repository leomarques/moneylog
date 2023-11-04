package lmm.moneylog.ui.features.account.detail.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.account.detail.view.components.AccountDetailDialogs
import lmm.moneylog.ui.features.account.detail.view.components.AccountDetailFields
import lmm.moneylog.ui.theme.DarkPurple
import lmm.moneylog.ui.theme.Size

@Composable
fun AccountDetailContent(
    name: String,
    color: Color,
    isEdit: Boolean,
    showArchiveConfirmDialog: Boolean,
    onArchiveConfirm: () -> Unit,
    onArchiveDismiss: () -> Unit,
    onColorPick: (Color) -> Unit,
    onNameChange: (String) -> Unit
) {
    Column(Modifier.padding(horizontal = Size.DefaultSpaceSize)) {
        val showColorsDialog = remember { mutableStateOf(false) }

        AccountDetailDialogs(
            showArchiveConfirmDialog = showArchiveConfirmDialog,
            showColorsDialog = showColorsDialog,
            onColorPick = onColorPick,
            onArchiveConfirm = onArchiveConfirm,
            onArchiveDismiss = onArchiveDismiss
        )

        AccountDetailFields(
            name = name,
            color = color,
            isEdit = isEdit,
            showColorsDialog = showColorsDialog,
            onNameChange = onNameChange
        )
    }
}

@Preview
@Composable
fun AccountDetailContentPreview() {
    AccountDetailContent(
        name = "Nubank",
        color = DarkPurple,
        isEdit = true,
        showArchiveConfirmDialog = false,
        onArchiveConfirm = {},
        onArchiveDismiss = {},
        onColorPick = {},
        onNameChange = {}
    )
}
