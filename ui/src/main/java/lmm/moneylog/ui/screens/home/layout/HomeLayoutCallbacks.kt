package lmm.moneylog.ui.screens.home.layout

import lmm.moneylog.ui.screens.home.components.cards.CreditCardsCardCallbacks
import lmm.moneylog.ui.screens.home.components.cards.TotalBalanceCardCallbacks

/**
 * Callbacks for HomeLayout interactions
 */
data class HomeLayoutCallbacks(
    val balanceCardCallbacks: TotalBalanceCardCallbacks = TotalBalanceCardCallbacks(),
    val onIncomeClick: () -> Unit = {},
    val onExpensesClick: () -> Unit = {},
    val creditCardsCallbacks: CreditCardsCardCallbacks = CreditCardsCardCallbacks()
)
