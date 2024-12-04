package lmm.moneylog.ui.features.transaction.list.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.creditcard.repositories.interfaces.GetCreditCardsRepository
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository
import lmm.moneylog.ui.features.transaction.list.model.TransactionsListUIState

const val GET_TRANSACTIONS_INCOME = "income"
const val GET_TRANSACTIONS_OUTCOME = "outcome"
const val GET_TRANSACTIONS_ALL = "all"

class TransactionsListViewModel(
    private val typeOfValue: String?,
    private val getTransactionsRepository: GetTransactionsRepository,
    private val getAccountsRepository: GetAccountsRepository,
    private val getCategoriesRepository: GetCategoriesRepository,
    private val getCreditCardsRepository: GetCreditCardsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionsListUIState(R.string.transactions))
    val uiState: StateFlow<TransactionsListUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val accounts = getAccountsRepository.getAccountsSuspend()
            val categories = getCategoriesRepository.getCategoriesSuspend()
            val creditCards = getCreditCardsRepository.getCreditCardsSuspend()

            val accountsMap =
                accounts.associate {
                    it.id to it.name
                }
            val creditCardMap =
                creditCards.associate {
                    it.id to it.name
                }
            val categoriesMap =
                categories.associate {
                    it.id to it.name
                }
            val categoriesColorMap =
                categories.associate {
                    it.id to Color(it.color.toULong())
                }

            when (typeOfValue) {
                GET_TRANSACTIONS_INCOME -> {
                    getTransactionsRepository.getIncomeTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toTransactionsListUiState(
                                titleResourceId = R.string.incomes,
                                accountMap = accountsMap,
                                creditCardMap = creditCardMap,
                                categoriesMap = categoriesMap,
                                categoriesColorMap = categoriesColorMap
                            )
                        }
                    }
                }

                GET_TRANSACTIONS_OUTCOME -> {
                    getTransactionsRepository.getOutcomeTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toTransactionsListUiState(
                                titleResourceId = R.string.outcomes,
                                accountMap = accountsMap,
                                creditCardMap = creditCardMap,
                                categoriesMap = categoriesMap,
                                categoriesColorMap = categoriesColorMap
                            )
                        }
                    }
                }

                else -> {
                    getTransactionsRepository.getAllTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toTransactionsListUiState(
                                titleResourceId = R.string.transactions,
                                accountMap = accountsMap,
                                creditCardMap = creditCardMap,
                                categoriesMap = categoriesMap,
                                categoriesColorMap = categoriesColorMap
                            )
                        }
                    }
                }
            }
        }
    }
}
