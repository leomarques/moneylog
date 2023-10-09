package lmm.moneylog.ui.navigation

import lmm.moneylog.ui.features.home.balancecard.balanceCardIncome
import lmm.moneylog.ui.features.home.balancecard.balanceCardOutcome
import lmm.moneylog.ui.features.transaction.gettransactions.getTransactionsAll
import lmm.moneylog.ui.features.transaction.gettransactions.getTransactionsIncome
import lmm.moneylog.ui.features.transaction.gettransactions.getTransactionsOutcome

fun convertTransactionTypeParam(type: String?): String = when (type) {
    balanceCardIncome -> getTransactionsIncome
    balanceCardOutcome -> getTransactionsOutcome
    else -> getTransactionsAll
}
