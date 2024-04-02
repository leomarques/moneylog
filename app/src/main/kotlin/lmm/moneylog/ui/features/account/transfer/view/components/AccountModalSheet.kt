package lmm.moneylog.ui.features.account.transfer.view.components

import androidx.compose.runtime.Composable
import lmm.moneylog.ui.components.bottomsheet.MyModalBottomSheet
import lmm.moneylog.ui.features.account.transfer.model.AccountTransferModel

@Composable
fun AccountModalSheet(
    list: List<AccountTransferModel>,
    filterName: String,
    onConfirm: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val filtered = list.filter { it.name != filterName }

    MyModalBottomSheet(
        list = filtered.map { it.name to it.color },
        onConfirm = { clickedIndex ->
            val originalIndex = list.indexOfFirst { it.id == filtered[clickedIndex].id }
            onConfirm(originalIndex)
        },
        onDismissRequest = onDismissRequest
    )
}
