package lmm.moneylog.ui.features.transaction.list.viewmodel

import androidx.compose.ui.graphics.Color
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.transaction.list.model.TransactionModel
import lmm.moneylog.ui.features.transaction.list.model.TransactionsListUIState
import lmm.moneylog.ui.theme.neutralColor

fun List<Transaction>.toModel(
    titleResourceId: Int,
    accountMap: Map<Int, String>,
    categoriesMap: Map<Int, String>,
    categoriesColorMap: Map<Int, Color>
): TransactionsListUIState {
    return TransactionsListUIState(
        transactions =
            sortedBy { it.date }.map { transaction ->
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
                        date = date.formatDate(),
                        id = id,
                        account = accountMap[accountId].orEmpty(),
                        category = categoriesMap[categoryId].orEmpty(),
                        categoryColor = categoriesColorMap[categoryId] ?: neutralColor
                    )
                }
            },
        titleResourceId = titleResourceId
    )
}

fun DomainTime.formatDate() = "$day/$month/$year"
