package lmm.moneylog.ui.features.transfer.list.viewmodel

import lmm.moneylog.data.accounttransfer.model.AccountTransfer
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.transfer.list.model.TransferModel
import lmm.moneylog.ui.features.transfer.list.model.TransfersListUIState

fun List<AccountTransfer>.toTransfersListUiState(
    accountMap: Map<Int, String>,
    titleResourceId: Int
): TransfersListUIState =
    TransfersListUIState(
        titleResourceId = titleResourceId,
        transfers = toTransferModels(accountMap)
    )

private fun List<AccountTransfer>.toTransferModels(
    accountMap: Map<Int, String>
) = sortedBy { it.year * 10000 + it.month * 100 + it.day }.map { transfer ->
    with(transfer) {
        TransferModel(
            value = value.formatForRs(),
            originAccount = accountMap[originAccountId].orEmpty(),
            destinationAccount = accountMap[destinationAccountId].orEmpty(),
            date = "$day/$month/$year",
            id = id
        )
    }
}
