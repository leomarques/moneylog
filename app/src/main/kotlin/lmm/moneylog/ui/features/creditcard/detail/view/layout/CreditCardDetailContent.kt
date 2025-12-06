package lmm.moneylog.ui.features.creditcard.detail.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.creditcard.detail.view.components.CreditCardDetailDialogs
import lmm.moneylog.ui.features.creditcard.detail.view.components.CreditCardDetailFields
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun CreditCardDetailContent(
    name: String,
    closingDay: String,
    dueDay: String,
    limit: String,
    color: Color,
    isEdit: Boolean,
    showDeleteConfirmDialog: Boolean,
    onColorPick: (Color) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onNameChange: (String) -> Unit,
    onClosingDayChange: (String) -> Unit,
    onDueDayDayChange: (String) -> Unit,
    onLimitChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Size.DefaultSpaceSize)
            .padding(top = Size.DefaultSpaceSize, bottom = Size.XLargeSpaceSize)
    ) {
        val showColorsDialog = remember { mutableStateOf(false) }

        CreditCardDetailDialogs(
            showDeleteConfirmDialog = showDeleteConfirmDialog,
            showColorsDialog = showColorsDialog.value,
            onColorPick = onColorPick,
            onDeleteConfirm = onDeleteConfirm,
            onDeleteDismiss = onDeleteDismiss,
            onShowColorsDialogChange = { showColorsDialog.value = it }
        )

        CreditCardDetailFields(
            name = name,
            isEdit = isEdit,
            onNameChange = onNameChange,
            color = color,
            onColorClick = { showColorsDialog.value = true },
            closingDay = closingDay,
            dueDay = dueDay,
            limit = limit,
            onClosingDayChange = onClosingDayChange,
            onDueDayDayChange = onDueDayDayChange,
            onLimitChange = onLimitChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreditCardDetailContentPreview() {
    CreditCardDetailContent(
        name = "Food",
        color = outcome,
        isEdit = true,
        showDeleteConfirmDialog = false,
        onColorPick = {},
        onDeleteConfirm = {},
        onDeleteDismiss = {},
        onNameChange = {},
        onClosingDayChange = {},
        onDueDayDayChange = {},
        onLimitChange = {},
        closingDay = "1",
        dueDay = "1",
        limit = "100"
    )
}
