package lmm.moneylog.ui.features.account.transfer.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import lmm.moneylog.ui.features.account.transfer.model.AccountTransferModel

@Composable
fun AccountTransferDialogs(
    list: List<AccountTransferModel>,
    showOriginAccountPicker: MutableState<Boolean>,
    showDestinationAccountPicker: MutableState<Boolean>,
    originFilterName: String,
    destinationFilterName: String,
    onOriginAccountPicked: (Int) -> Unit,
    onDestinationAccountPicked: (Int) -> Unit
) {
    if (showOriginAccountPicker.value) {
        AccountModalSheet(
            list = list,
            filterName = originFilterName,
            onConfirm = onOriginAccountPicked,
            onDismissRequest = { showOriginAccountPicker.value = false }
        )
    }

    if (showDestinationAccountPicker.value) {
        AccountModalSheet(
            list = list,
            filterName = destinationFilterName,
            onConfirm = onDestinationAccountPicked,
            onDismissRequest = { showDestinationAccountPicker.value = false }
        )
    }
}
