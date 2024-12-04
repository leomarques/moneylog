package lmm.moneylog.ui.features.transaction.list.model

fun List<TransactionModel>.filtered(filter: String): List<TransactionModel> {
    return filter { transaction ->
        transaction.description.startsWith(
            prefix = filter,
            ignoreCase = true
        )
    }
}
