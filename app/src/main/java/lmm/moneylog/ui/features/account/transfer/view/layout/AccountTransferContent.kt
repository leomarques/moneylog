package lmm.moneylog.ui.features.account.transfer.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

@Composable
fun AccountTransferContent(
    value: String,
    list: List<AccountTransferModel>,
    originAccountDisplay: String,
    destinationAccountDisplay: String,
    originAccountColor: Color,
    destinationAccountColor: Color,
    onOriginAccountPicked: (Int) -> Unit,
    onDestinationAccountPicked: (Int) -> Unit,
    onValueChange: (String) -> Unit
) {
    Column(Modifier.padding(horizontal = Size.DefaultSpaceSize)) {
        val showOriginAccountPicker = remember { mutableStateOf(false) }
        val showDestinationAccountPicker = remember { mutableStateOf(false) }

        AccountTransferDialogs(
            list = list,
            showOriginAccountPicker = showOriginAccountPicker,
            showDestinationAccountPicker = showDestinationAccountPicker,
            originFilterName = destinationAccountDisplay,
            destinationFilterName = originAccountDisplay,
            onOriginAccountPicked = onOriginAccountPicked,
            onDestinationAccountPicked = onDestinationAccountPicked
        )

        AccountTransferFields(
            value = value,
            list = list,
            originAccountDisplay = originAccountDisplay,
            originAccountColor = originAccountColor,
            destinationAccountDisplay = destinationAccountDisplay,
            destinationAccountColor = destinationAccountColor,
            onValueChange = onValueChange,
            onOriginAccountPicked = { showOriginAccountPicker.value = true },
            onDestinationAccountPicked = { showDestinationAccountPicker.value = true }
        )
    }
}

@Preview
@Composable
fun AccountTransferContentPreview() {
    AccountTransferContent(
        value = "",
        list = emptyList(),
        originAccountDisplay = "Nubank",
        destinationAccountDisplay = "Itaú",
        originAccountColor = Color.Red,
        destinationAccountColor = Color.Blue,
        onOriginAccountPicked = {},
        onDestinationAccountPicked = {},
        onValueChange = {}
    )
}
