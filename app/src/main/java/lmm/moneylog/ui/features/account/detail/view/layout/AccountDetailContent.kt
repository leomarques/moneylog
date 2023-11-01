package lmm.moneylog.ui.features.account.detail.view.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import lmm.moneylog.ui.components.icons.AccountIcon
import lmm.moneylog.ui.components.icons.BrushIcon
import lmm.moneylog.ui.components.misc.ColorPicker
import lmm.moneylog.ui.components.textfields.ColorTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.features.account.archive.view.components.ArchiveAccountConfirmDialog
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
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = Size.DefaultSpaceSize)
    ) {
        var showColorsDialog by remember { mutableStateOf(false) }

        if (showColorsDialog) {
            ColorPicker(
                onConfirm = {
                    showColorsDialog = false
                    onColorPick(it)
                },
                onDismiss = { showColorsDialog = false }
            )
        }

        if (showArchiveConfirmDialog) {
            ArchiveAccountConfirmDialog(
                onConfirm = onArchiveConfirm,
                onDismiss = onArchiveDismiss
            )
        }

        StateTextField(
            modifier = Modifier.padding(bottom = Size.MediumSpaceSize),
            value = name,
            title = stringResource(R.string.name),
            leadingIcon = { AccountIcon() },
            keyboardType = KeyboardType.Text,
            getFocus = !isEdit,
            onValueChange = onNameChange
        )

        ColorTextField(
            color = color,
            leadingIcon = { BrushIcon() },
            onClick = {
                showColorsDialog = true
            }
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
