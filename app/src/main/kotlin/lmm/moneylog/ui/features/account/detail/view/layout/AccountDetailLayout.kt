package lmm.moneylog.ui.features.account.detail.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
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
    onCalculateAdjustment: suspend (String) -> Pair<String, Double>?,
    onAdjustBalanceFinalConfirm: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var showArchiveConfirmDialog by remember { mutableStateOf(false) }
    var showAdjustBalanceDialog by remember { mutableStateOf(false) }
    var showAdjustBalanceConfirmDialog by remember { mutableStateOf(false) }
    var adjustmentValueDisplay by remember { mutableStateOf("") }
    var adjustmentValue by remember { mutableDoubleStateOf(0.0) }

    Scaffold(
        modifier = modifier,
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
                    onNameChange = onNameChange,
                    showAdjustBalanceDialog = showAdjustBalanceDialog,
                    onAdjustBalanceInputConfirm = { newBalance ->
                        kotlinx.coroutines.MainScope().launch {
                            val result = onCalculateAdjustment(newBalance)
                            if (result != null) {
                                adjustmentValueDisplay = result.first
                                adjustmentValue = result.second
                                showAdjustBalanceDialog = false
                                showAdjustBalanceConfirmDialog = true
                            }
                        }
                    },
                    onAdjustBalanceDismiss = { showAdjustBalanceDialog = false },
                    onAdjustBalanceClick = { showAdjustBalanceDialog = true },
                    showAdjustBalanceConfirmDialog = showAdjustBalanceConfirmDialog,
                    adjustmentValue = adjustmentValueDisplay,
                    onAdjustBalanceFinalConfirm = {
                        showAdjustBalanceConfirmDialog = false
                        onAdjustBalanceFinalConfirm(adjustmentValue)
                    },
                    onAdjustBalanceConfirmDismiss = {
                        showAdjustBalanceConfirmDialog = false
                        showAdjustBalanceDialog = true
                    }
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
        onNameChange = {},
        onCalculateAdjustment = { null },
        onAdjustBalanceFinalConfirm = { }
    )
}
