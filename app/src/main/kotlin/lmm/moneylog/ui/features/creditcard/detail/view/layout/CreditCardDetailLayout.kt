package lmm.moneylog.ui.features.creditcard.detail.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.features.creditcard.detail.view.components.CreditCardDetailTopBar
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun CreditCardDetailLayout(
    name: String,
    color: Color,
    isEdit: Boolean,
    showFab: Boolean,
    closingDay: Int,
    dueDay: Int,
    limit: Int,
    onArrowBackClick: () -> Unit,
    onColorPicked: (Color) -> Unit,
    onNameChange: (String) -> Unit,
    onFabClick: () -> Unit,
    onClosingDayChange: (String) -> Unit,
    onDueDayDayChange: (String) -> Unit,
    onLimitChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteConfirmClick: () -> Unit = {},
) {
    val showDeleteConfirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CreditCardDetailTopBar(
                isEdit = isEdit,
                onArrowBackClick = onArrowBackClick,
                onDeleteIconClick = { showDeleteConfirmDialog.value = true }
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
                CreditCardDetailContent(
                    name = name,
                    color = color,
                    isEdit = isEdit,
                    showDeleteConfirmDialog = showDeleteConfirmDialog.value,
                    onColorPicked = onColorPicked,
                    onNameChange = onNameChange,
                    onDeleteDismiss = { showDeleteConfirmDialog.value = false },
                    onDeleteConfirm = {
                        showDeleteConfirmDialog.value = false
                        onDeleteConfirmClick()
                    },
                    onClosingDayChange = onClosingDayChange,
                    onDueDayDayChange = onDueDayDayChange,
                    onLimitChange = onLimitChange,
                    closingDay = closingDay,
                    dueDay = dueDay,
                    limit = limit
                )
            }
        }
    )
}

@Preview
@Composable
private fun CreditCardDetailLayoutPreview() {
    CreditCardDetailLayout(
        name = "",
        color = neutralColor,
        isEdit = false,
        showFab = true,
        onArrowBackClick = {},
        onColorPicked = {},
        onDeleteConfirmClick = {},
        onFabClick = {},
        onNameChange = {},
        closingDay = 1,
        dueDay = 1,
        limit = 1,
        onClosingDayChange = {},
        onDueDayDayChange = {},
        onLimitChange = {}
    )
}
