package lmm.moneylog.ui.features.account.transfer.view.layout

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
import lmm.moneylog.ui.features.account.transfer.model.AccountTransferModel
import lmm.moneylog.ui.features.account.transfer.view.components.AccountTransferDialogs
import lmm.moneylog.ui.features.account.transfer.view.components.AccountTransferFields
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun AccountTransferContent(
    value: String,
    list: List<AccountTransferModel>,
    originAccountDisplay: String,
    destinationAccountDisplay: String,
    originAccountColor: Color,
    destinationAccountColor: Color,
    onOriginAccountPick: (Int) -> Unit,
    onDestinationAccountPick: (Int) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val showOriginAccountPicker = remember { mutableStateOf(false) }
    val showDestinationAccountPicker = remember { mutableStateOf(false) }

    AccountTransferDialogs(
        list = list,
        showOriginAccountPicker = showOriginAccountPicker,
        showDestinationAccountPicker = showDestinationAccountPicker,
        originFilterName = destinationAccountDisplay,
        destinationFilterName = originAccountDisplay,
        onOriginAccountPick = onOriginAccountPick,
        onDestinationAccountPick = onDestinationAccountPick
    )

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Size.DefaultSpaceSize)
            .padding(top = Size.DefaultSpaceSize, bottom = Size.XLargeSpaceSize)
    ) {
        AccountTransferFields(
            value = value,
            list = list,
            originAccountDisplay = originAccountDisplay,
            originAccountColor = originAccountColor,
            destinationAccountDisplay = destinationAccountDisplay,
            destinationAccountColor = destinationAccountColor,
            onValueChange = onValueChange,
            onOriginAccountPick = { showOriginAccountPicker.value = true },
            onDestinationAccountPick = { showDestinationAccountPicker.value = true }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountTransferContentPreview() {
    AccountTransferContent(
        value = "",
        list = emptyList(),
        originAccountDisplay = "Nubank",
        destinationAccountDisplay = "Itaú",
        originAccountColor = outcome,
        destinationAccountColor = Color.Blue,
        onOriginAccountPick = {},
        onDestinationAccountPick = {},
        onValueChange = {}
    )
}
