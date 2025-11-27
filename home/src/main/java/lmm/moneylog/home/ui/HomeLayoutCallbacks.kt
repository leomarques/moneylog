package lmm.moneylog.home.ui

import lmm.moneylog.home.ui.cards.CreditCardsCardCallbacks
import lmm.moneylog.home.ui.cards.TotalBalanceCardCallbacks

/**
 * Callbacks for HomeLayout interactions
 *
 * @property balanceCardCallbacks Callbacks for the total balance card interactions
 * @property onIncomeClick Callback invoked when the income card is clicked
 * @property onExpensesClick Callback invoked when the expenses card is clicked
 * @property creditCardsCallbacks Callbacks for credit card interactions
 * @property onFabClick Callback invoked when the floating action button is clicked
 * @property onMaskToggle Callback invoked when the mask toggle icon is clicked
 */
data class HomeLayoutCallbacks(
    val balanceCardCallbacks: TotalBalanceCardCallbacks = TotalBalanceCardCallbacks(),
    val onIncomeClick: () -> Unit = {},
    val onExpensesClick: () -> Unit = {},
    val creditCardsCallbacks: CreditCardsCardCallbacks = CreditCardsCardCallbacks(),
    val onFabClick: () -> Unit = {},
    val onMaskToggle: () -> Unit = {}
)
