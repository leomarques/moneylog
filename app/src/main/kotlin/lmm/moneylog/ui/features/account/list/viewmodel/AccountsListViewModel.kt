package lmm.moneylog.ui.features.account.list.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.balance.interactors.GetBalanceByAccountInteractor
import lmm.moneylog.data.balance.repositories.GetBalanceRepository
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.account.list.model.AccountModel
import lmm.moneylog.ui.features.account.list.model.AccountsListUIState
import lmm.moneylog.ui.features.category.list.model.CategoriesListUIState
import lmm.moneylog.ui.features.category.list.viewmodel.toCategoryModelList

class AccountsListViewModel(
    getAccountsRepository: GetAccountsRepository,
    accountTransferRepository: AccountTransferRepository,
    getBalanceRepository: GetBalanceRepository,
    private val getBalanceByAccountInteractor: GetBalanceByAccountInteractor,
    private val addTransactionRepository: AddTransactionRepository,
    private val domainTimeRepository: DomainTimeRepository,
    private val getCategoriesRepository: GetCategoriesRepository
) : ViewModel() {
    private val accountsFlow = getAccountsRepository.getAccounts()
    private val transfersFlow = accountTransferRepository.getTransfers()
    private val transactionsFlow = getBalanceRepository.getTransactions()

    companion object {
        private const val TAG = "AccountsListViewModel"
    }

    private val _categoriesState = MutableStateFlow(CategoriesListUIState())
    val categoriesState: StateFlow<CategoriesListUIState> = _categoriesState.asStateFlow()

    init {
        viewModelScope.launch {
            getCategoriesRepository.getCategories().collect { categories ->
                _categoriesState.update {
                    CategoriesListUIState(
                        categories
                            .toCategoryModelList()
                            .reversed()
                    )
                }
            }
        }
    }

    var uiState =
        combine(
            accountsFlow,
            transfersFlow,
            transactionsFlow
        ) { accounts, transfers, transactions ->
            val list = mutableListOf<AccountModel>()

            accounts.forEach { account ->
                // Calculate balance from transactions
                var balance =
                    transactions
                        .filter { it.accountId == account.id }
                        .sumOf { it.value }

                // Adjust for transfers
                transfers
                    .filter { it.originAccountId == account.id }
                    .forEach { balance -= it.value }

                transfers
                    .filter { it.destinationAccountId == account.id }
                    .forEach { balance += it.value }

                list.add(
                    AccountModel(
                        id = account.id,
                        name = account.name,
                        balance = balance.formatForRs(),
                        color = account.color.toComposeColor()
                    )
                )
            }

            AccountsListUIState(list.reversed())
        }.stateIn(viewModelScope, SharingStarted.Lazily, AccountsListUIState())

    @Suppress("ReturnCount")
    suspend fun calculateAdjustment(
        accountId: Int,
        newBalance: String
    ): Pair<String, Double>? {
        val newBalanceValue = newBalance.toDoubleOrNull() ?: return null

        val currentBalance =
            try {
                getBalanceByAccountInteractor.execute(accountId)
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Error getting balance for account $accountId", e)
                return null
            } catch (e: NumberFormatException) {
                Log.e(TAG, "Number format error calculating balance for account $accountId", e)
                return null
            }

        val adjustmentValue = newBalanceValue - currentBalance
        if (adjustmentValue == 0.0) return null

        // Format the adjustment value for display
        val formattedValue =
            if (adjustmentValue > 0) {
                "+${adjustmentValue.formatForRs()}"
            } else {
                adjustmentValue.formatForRs()
            }

        return Pair(formattedValue, adjustmentValue)
    }

    fun onAdjustBalanceConfirm(
        accountId: Int,
        adjustmentValue: Double,
        categoryId: Int?,
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val adjustmentTransaction =
                    Transaction(
                        value = adjustmentValue,
                        description = "Adjustment",
                        date = domainTimeRepository.getCurrentDomainTime(),
                        accountId = accountId,
                        categoryId = if (categoryId != null && categoryId > 0) categoryId else null
                    )
                addTransactionRepository.save(adjustmentTransaction)
                onSuccess()
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Error saving balance adjustment transaction", e)
                onError(lmm.moneylog.R.string.validation_invalid_data)
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Invalid argument for balance adjustment transaction", e)
                onError(lmm.moneylog.R.string.validation_invalid_data)
            }
        }
    }
}
