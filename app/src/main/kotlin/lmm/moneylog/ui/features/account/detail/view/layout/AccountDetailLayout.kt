package lmm.moneylog.ui.features.account.detail.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.features.account.detail.view.components.AccountDetailTopAppBar
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun AccountDetailLayout(
    name: String,
    color: Color,
    isEdit: Boolean,
    showFab: Boolean,
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onArchiveConfirm: () -> Unit,
    onColorPick: (Color) -> Unit,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showArchiveConfirmDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AccountDetailTopAppBar(
                isEdit = isEdit,
                onArrowBackClick = onArrowBackClick,
                onArchiveIconClick = { showArchiveConfirmDialog = true }
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
                AccountDetailContent(
                    name = name,
                    color = color,
                    isEdit = isEdit,
                    showArchiveConfirmDialog = showArchiveConfirmDialog,
                    onArchiveConfirm = {
                        showArchiveConfirmDialog = false
                        onArchiveConfirm()
                    },
                    onArchiveDismiss = { showArchiveConfirmDialog = false },
                    onColorPick = onColorPick,
                    onNameChange = onNameChange
                )
            }
        }
    )
}

@Preview
@Composable
private fun AccountDetailLayoutPreview() {
    AccountDetailLayout(
        name = "",
        color = neutralColor,
        isEdit = false,
        showFab = true,
        onArrowBackClick = {},
        onFabClick = {},
        onArchiveConfirm = {},
        onColorPick = {},
        onNameChange = {}
    )
}
