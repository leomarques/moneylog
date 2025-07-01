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
import lmm.moneylog.data.time.repositories.DomainTimeRepository
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
    private val timeRepository: DomainTimeRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            TransactionsListUIState(
                titleResourceId = R.string.transactions
            )
        )
    val uiState: StateFlow<TransactionsListUIState> = _uiState.asStateFlow()

    private var month = timeRepository.getCurrentDomainTime().month
    private var year = timeRepository.getCurrentDomainTime().year

    init {
        refreshTransactions()
    }

    private fun refreshTransactions() {
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

            val monthName = timeRepository.getMonthName(month)

            when (typeOfValue) {
                GET_TRANSACTIONS_INCOME -> {
                    getTransactionsRepository.getIncomeTransactions(
                        month = month,
                        year = year
                    ).collect { transactions ->
                        _uiState.update {
                            transactions.toTransactionsListUiState(
                                titleResourceId = R.string.incomes,
                                accountMap = accountsMap,
                                creditCardMap = creditCardMap,
                                categoriesMap = categoriesMap,
                                categoriesColorMap = categoriesColorMap
                            ).copy(
                                monthName = monthName,
                                total = transactions.sumOf { it.value }
                            )
                        }
                    }
                }

                GET_TRANSACTIONS_OUTCOME -> {
                    getTransactionsRepository.getOutcomeTransactions(
                        month = month,
                        year = year
                    ).collect { transactions ->
                        _uiState.update {
                            transactions.toTransactionsListUiState(
                                titleResourceId = R.string.outcomes,
                                accountMap = accountsMap,
                                creditCardMap = creditCardMap,
                                categoriesMap = categoriesMap,
                                categoriesColorMap = categoriesColorMap
                            ).copy(
                                monthName = monthName,
                                total = transactions.sumOf { it.value }
                            )
                        }
                    }
                }

                else -> {
                    getTransactionsRepository.getAllTransactions(
                        month = month,
                        year = year
                    ).collect { transactions ->
                        _uiState.update {
                            transactions.toTransactionsListUiState(
                                titleResourceId = R.string.transactions,
                                accountMap = accountsMap,
                                creditCardMap = creditCardMap,
                                categoriesMap = categoriesMap,
                                categoriesColorMap = categoriesColorMap
                            ).copy(
                                monthName = monthName,
                                total = transactions.sumOf { it.value }
                            )
                        }
                    }
                }
            }
        }
    }

    fun onPreviousMonthClick() {
        if (month == 1) {
            month = 12
            year--
        } else {
            month--
        }

        refreshTransactions()
    }

    fun onNextMonthClick() {
        if (month == 12) {
            month = 1
            year++
        } else {
            month++
        }

        refreshTransactions()
    }
}
