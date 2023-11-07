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
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.category.repositories.GetCategoriesRepository
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.repositories.GetTransactionsRepository
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.ui.features.transaction.list.model.GetTransactionsModel
import lmm.moneylog.ui.features.transaction.list.model.TransactionModel
import lmm.moneylog.ui.misc.formatForRs

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
            R.string.transactions
        )
    )
    val uiState: StateFlow<GetTransactionsModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val accounts = getAccountsRepository.getAccountsSuspend()
            val categories = getCategoriesRepository.getCategoriesSuspend()

            val accountsMap = accounts.associate {
                it.id to it.name
            }
            val categoriesMap = categories.associate {
                it.id to it.name
            }
            val categoriesColorMap = categories.associate {
                it.id to Color(it.color.toULong())
            }

            when (typeOfValue) {
                getTransactionsIncome -> {
                    getTransactionsRepository.getIncomeTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toModel(
                                R.string.incomes,
                                accountMap = accountsMap,
                                categoriesMap = categoriesMap,
                                categoriesColorMap = categoriesColorMap
                            )
                        }
                    }
                }

                getTransactionsOutcome -> {
                    getTransactionsRepository.getOutcomeTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toModel(
                                R.string.outcomes,
                                accountMap = accountsMap,
                                categoriesMap = categoriesMap,
                                categoriesColorMap = categoriesColorMap
                            )
                        }
                    }
                }

                else -> {
                    getTransactionsRepository.getAllTransactions().collect { transactions ->
                        _uiState.update {
                            transactions.toModel(
                                R.string.transactions,
                                accountMap = accountsMap,
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

private fun List<Transaction>.toModel(
    titleResourceId: Int,
    accountMap: Map<Int, String>,
    categoriesMap: Map<Int, String>,
    categoriesColorMap: Map<Int, Color>
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
                    category = categoriesMap[categoryId].orEmpty(),
                    categoryColor = categoriesColorMap[categoryId] ?: Color.Gray
                )
            }
        },
        titleResourceId = titleResourceId
    )
}

fun DomainTime.formatDate() = "$day/$month/$year"
