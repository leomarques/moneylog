package lmm.moneylog.ui.features.transaction.gettransactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.category.repositories.GetCategoriesRepository
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.repositories.GetTransactionsRepository
import lmm.moneylog.ui.textformatters.formatDate
import lmm.moneylog.ui.textformatters.formatForRs

const val getTransactionsIncome = "income"
const val getTransactionsOutcome = "outcome"
const val getTransactionsAll = "all"

class GetTransactionsViewModel(
    private val typeOfValue: String?,
    private val getTransactionsRepository: GetTransactionsRepository,
    private val getAccountsRepository: GetAccountsRepository,
    private val getCategoriesRepository: GetCategoriesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        GetTransactionsModel(
            titleResourceId =
            R.string.gettransactions_topbar_all
        )
    )
    val uiState: StateFlow<GetTransactionsModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val accounts = async { getAccountsRepository.getAccountsSuspend() }
            val categories = async { getCategoriesRepository.getCategoriesSuspend() }

            val accountsMap = accounts.await().associate {
                it.id to it.name
            }
            val categoriesMap = categories.await().associate {
                it.id to it.name
            }

            when (typeOfValue) {
                getTransactionsIncome -> {
                    getTransactionsRepository.getIncomeTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toModel(
                                R.string.gettransactions_topbar_income,
                                accountMap = accountsMap,
                                categoryMap = categoriesMap
                            )
                        }
                    }
                }

                getTransactionsOutcome -> {
                    getTransactionsRepository.getOutcomeTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toModel(
                                R.string.gettransactions_topbar_outcome,
                                accountMap = accountsMap,
                                categoryMap = categoriesMap
                            )
                        }
                    }
                }

                else -> {
                    getTransactionsRepository.getAllTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toModel(
                                R.string.gettransactions_topbar_all,
                                accountMap = accountsMap,
                                categoryMap = categoriesMap
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun List<Transaction>.toModel(
    titleResourceId: Int,
    accountMap: Map<Int, String>,
    categoryMap: Map<Int, String>
): GetTransactionsModel {
    return GetTransactionsModel(
        transactions = sortedBy { it.date }.map { transaction ->
            with(transaction) {
                TransactionModel(
                    value = if (value < 0.0) {
                        (-value).formatForRs()
                    } else {
                        value.formatForRs()
                    },
                    isIncome = value > 0,
                    description = description,
                    date = date.formatDate(),
                    id = id,
                    account = accountMap[accountId].orEmpty(),
                    category = categoryMap[categoryId].orEmpty()
                )
            }
        },
        titleResourceId = titleResourceId
    )
}
