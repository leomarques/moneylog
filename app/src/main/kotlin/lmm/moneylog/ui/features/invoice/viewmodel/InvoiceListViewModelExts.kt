package lmm.moneylog.ui.features.invoice.viewmodel

import androidx.compose.ui.graphics.Color
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.invoice.model.InvoiceListUIState
import lmm.moneylog.ui.features.transaction.list.model.TransactionModel
import lmm.moneylog.ui.theme.neutralColor

fun List<Transaction>.toInvoiceListUiState(
    titleResourceId: Int,
    categoriesMap: Map<Int, String>,
    categoriesColorMap: Map<Int, Color>,
    accounts: List<Account>
): InvoiceListUIState =
    InvoiceListUIState(
        titleResourceId = titleResourceId,
        transactions =
            toTransactionModels(
                categoriesMap = categoriesMap,
                categoriesColorMap = categoriesColorMap
            ),
        accounts = accounts,
    )

private fun List<Transaction>.toTransactionModels(
    categoriesMap: Map<Int, String>,
    categoriesColorMap: Map<Int, Color>
) = sortedBy { it.date }.map { transaction ->
    with(transaction) {
        TransactionModel(
            value = value.formatForRs(false),
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
