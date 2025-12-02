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
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.darkPurple

@Composable
fun AccountDetailContent(
    name: String,
    color: Color,
    isEdit: Boolean,
    showArchiveConfirmDialog: Boolean,
    onArchiveConfirm: () -> Unit,
    onArchiveDismiss: () -> Unit,
    onColorPick: (Color) -> Unit,
    onNameChange: (String) -> Unit,
    showAdjustBalanceDialog: Boolean,
    onAdjustBalanceInputConfirm: (String) -> Unit,
    onAdjustBalanceDismiss: () -> Unit,
    onAdjustBalanceClick: () -> Unit,
    showAdjustBalanceConfirmDialog: Boolean,
    adjustmentValue: String,
    onAdjustBalanceFinalConfirm: () -> Unit,
    onAdjustBalanceConfirmDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(horizontal = Size.DefaultSpaceSize)) {
        val showColorsDialog = remember { mutableStateOf(false) }

        AccountDetailDialogs(
            showArchiveConfirmDialog = showArchiveConfirmDialog,
            showColorsDialog = showColorsDialog,
            onColorPick = onColorPick,
            onArchiveConfirm = onArchiveConfirm,
            onArchiveDismiss = onArchiveDismiss,
            showAdjustBalanceDialog = showAdjustBalanceDialog,
            onAdjustBalanceInputConfirm = onAdjustBalanceInputConfirm,
            onAdjustBalanceDismiss = onAdjustBalanceDismiss,
            showAdjustBalanceConfirmDialog = showAdjustBalanceConfirmDialog,
            adjustmentValue = adjustmentValue,
            onAdjustBalanceFinalConfirm = onAdjustBalanceFinalConfirm,
            onAdjustBalanceConfirmDismiss = onAdjustBalanceConfirmDismiss
        )

        AccountDetailFields(
            name = name,
            color = color,
            isEdit = isEdit,
            onNameChange = onNameChange,
            onColorDialogClick = { showColorsDialog.value = true },
            onAdjustBalanceClick = onAdjustBalanceClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountDetailContentPreview() {
    AccountDetailContent(
        name = "Nubank",
        color = darkPurple,
        isEdit = true,
        showArchiveConfirmDialog = false,
        onArchiveConfirm = {},
        onArchiveDismiss = {},
        onColorPick = {},
        onNameChange = {},
        showAdjustBalanceDialog = false,
        onAdjustBalanceInputConfirm = {},
        onAdjustBalanceDismiss = {},
        onAdjustBalanceClick = {},
        showAdjustBalanceConfirmDialog = false,
        adjustmentValue = "",
        onAdjustBalanceFinalConfirm = {},
        onAdjustBalanceConfirmDismiss = {}
    )
}
