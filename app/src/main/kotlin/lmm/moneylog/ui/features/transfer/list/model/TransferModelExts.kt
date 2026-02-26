package lmm.moneylog.ui.features.transfer.list.model

fun List<TransferModel>.filtered(filter: String): List<TransferModel> =
    filter { transfer ->
        transfer.originAccount.startsWith(
            prefix = filter,
            ignoreCase = true
        ) || transfer.destinationAccount.startsWith(
            prefix = filter,
            ignoreCase = true
        )
    }
