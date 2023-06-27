package lmm.moneylog.ui

import lmm.moneylog.data.gettransactions.getTransactionsAll
import lmm.moneylog.data.gettransactions.getTransactionsIncome
import lmm.moneylog.data.gettransactions.getTransactionsOutcome
import lmm.moneylog.ui.features.home.balancecard.balanceCardIncome
import lmm.moneylog.ui.features.home.balancecard.balanceCardOutcome

fun convertTransactionTypeParam(type: String?): String = when (type) {
    balanceCardIncome -> getTransactionsIncome
    balanceCardOutcome -> getTransactionsOutcome
    else -> getTransactionsAll
}
