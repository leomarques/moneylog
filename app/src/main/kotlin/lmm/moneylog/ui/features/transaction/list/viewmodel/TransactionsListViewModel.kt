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

private const val MONTH_JANUARY = 1
private const val MONTH_DECEMBER = 12

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
            val lookupMaps = buildLookupMaps()
            val monthName = timeRepository.getMonthName(month)
            collectTransactionsByType(lookupMaps, monthName)
        }
    }

    private suspend fun buildLookupMaps(): LookupMaps {
        val accounts = getAccountsRepository.getAccountsSuspend()
        val categories = getCategoriesRepository.getCategoriesSuspend()
        val creditCards = getCreditCardsRepository.getCreditCardsSuspend()

        return LookupMaps(
            accountsMap = accounts.associate { it.id to it.name },
            creditCardMap = creditCards.associate { it.id to it.name },
            categoriesMap = categories.associate { it.id to it.name },
            categoriesColorMap = categories.associate { it.id to Color(it.color.toULong()) }
        )
    }

    private suspend fun collectTransactionsByType(
        lookupMaps: LookupMaps,
        monthName: String
    ) {
        when (typeOfValue) {
            GET_TRANSACTIONS_INCOME -> {
                collectIncomeTransactions(lookupMaps, monthName)
            }

            GET_TRANSACTIONS_OUTCOME -> {
                collectOutcomeTransactions(lookupMaps, monthName)
            }

            else -> {
                collectAllTransactions(lookupMaps, monthName)
            }
        }
    }

    private suspend fun collectIncomeTransactions(
        lookupMaps: LookupMaps,
        monthName: String
    ) {
        getTransactionsRepository
            .getIncomeTransactions(month = month, year = year)
            .collect { transactions ->
                updateUiState(
                    transactions = transactions,
                    titleResourceId = R.string.incomes,
                    lookupMaps = lookupMaps,
                    monthName = monthName
                )
            }
    }

    private suspend fun collectOutcomeTransactions(
        lookupMaps: LookupMaps,
        monthName: String
    ) {
        getTransactionsRepository
            .getOutcomeTransactions(month = month, year = year)
            .collect { transactions ->
                updateUiState(
                    transactions = transactions,
                    titleResourceId = R.string.outcomes,
                    lookupMaps = lookupMaps,
                    monthName = monthName
                )
            }
    }

    private suspend fun collectAllTransactions(
        lookupMaps: LookupMaps,
        monthName: String
    ) {
        getTransactionsRepository
            .getAllTransactions(month = month, year = year)
            .collect { transactions ->
                updateUiState(
                    transactions = transactions,
                    titleResourceId = R.string.transactions,
                    lookupMaps = lookupMaps,
                    monthName = monthName
                )
            }
    }

    private fun updateUiState(
        transactions: List<lmm.moneylog.data.transaction.model.Transaction>,
        titleResourceId: Int,
        lookupMaps: LookupMaps,
        monthName: String
    ) {
        _uiState.update {
            transactions
                .toTransactionsListUiState(
                    titleResourceId = titleResourceId,
                    accountMap = lookupMaps.accountsMap,
                    creditCardMap = lookupMaps.creditCardMap,
                    categoriesMap = lookupMaps.categoriesMap,
                    categoriesColorMap = lookupMaps.categoriesColorMap
                ).copy(
                    monthName = monthName,
                    total = transactions.sumOf { it.value }
                )
        }
    }

    private data class LookupMaps(
        val accountsMap: Map<Int, String>,
        val creditCardMap: Map<Int, String>,
        val categoriesMap: Map<Int, String>,
        val categoriesColorMap: Map<Int, Color>
    )

    fun onPreviousMonthClick() {
        if (month == MONTH_JANUARY) {
            month = MONTH_DECEMBER
            year--
        } else {
            month--
        }

        refreshTransactions()
    }

    fun onNextMonthClick() {
        if (month == MONTH_DECEMBER) {
            month = MONTH_JANUARY
            year++
        } else {
            month++
        }

        refreshTransactions()
    }
}
