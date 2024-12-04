package lmm.moneylog.ui.features.invoice

import androidx.compose.ui.graphics.Color
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.transaction.list.model.TransactionModel
import lmm.moneylog.ui.theme.neutralColor

fun List<Transaction>.toInvoiceListUiState(
    titleResourceId: Int,
    categoriesMap: Map<Int, String>,
    categoriesColorMap: Map<Int, Color>
): InvoiceListUIState {
    return InvoiceListUIState(
        transactions =
            toTransactionModels(
                categoriesMap = categoriesMap,
                categoriesColorMap = categoriesColorMap
            ),
        titleResourceId = titleResourceId
    )
}

private fun List<Transaction>.toTransactionModels(
    categoriesMap: Map<Int, String>,
    categoriesColorMap: Map<Int, Color>
) = sortedBy { it.date }.map { transaction ->
    with(transaction) {
        TransactionModel(
            value =
                if (value < 0.0) {
                    (-value).formatForRs()
                } else {
                    value.formatForRs()
                },
            isIncome = value > 0,
            description = description,
            date = date.getFormattedDate(),
            id = id,
            account = "",
            creditCard = "",
            category = categoriesMap[categoryId].orEmpty(),
            categoryColor = categoriesColorMap[categoryId] ?: neutralColor
        )
    }
}
