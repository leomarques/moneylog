package lmm.moneylog.ui.features.account.detail.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.AddAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.ArchiveAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.account.repositories.interfaces.UpdateAccountRepository
import lmm.moneylog.data.balance.interactors.GetBalanceByAccountInteractor
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.AddTransactionRepository
import lmm.moneylog.ui.extensions.getIdParam
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.account.detail.model.AccountDetailUIState

class AccountDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getAccountsRepository: GetAccountsRepository,
    private val addAccountRepository: AddAccountRepository,
    private val updateAccountRepository: UpdateAccountRepository,
    private val archiveAccountRepository: ArchiveAccountRepository,
    private val getBalanceByAccountInteractor: GetBalanceByAccountInteractor,
    private val addTransactionRepository: AddTransactionRepository,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountDetailUIState())
    val uiState: StateFlow<AccountDetailUIState> = _uiState.asStateFlow()

    companion object {
        private const val TAG = "AccountDetailViewModel"
    }

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getAccountsRepository.getAccountById(id)?.let { account ->
                    _uiState.update {
                        AccountDetailUIState(
                            id = account.id,
                            name = account.name,
                            color = account.color.toComposeColor(),
                            isEdit = true
                        )
                    }
                }
            }
        }
    }

    fun archiveAccount() {
        viewModelScope.launch {
            archiveAccountRepository.updateArchived(
                id = _uiState.value.id,
                archived = true
            )
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onColorPick(color: Color) {
        _uiState.update { it.copy(color = color) }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        val state = _uiState.value
        if (state.name.trim().isEmpty()) {
            onError(R.string.detail_no_name)
            return
        }

        _uiState.update { it.copy(showFab = false) }

        viewModelScope.launch {
            if (state.isEdit) {
                updateAccountRepository.update(state.toAccount())
            } else {
                addAccountRepository.save(state.toAccount())
            }

            onSuccess()
        }
    }

    @Suppress("ReturnCount")
    suspend fun calculateAdjustment(newBalance: String): Pair<String, Double>? {
        val state = _uiState.value
        if (!state.isEdit) return null

        val newBalanceValue = newBalance.toDoubleOrNull() ?: return null

        val currentBalance =
            try {
                getBalanceByAccountInteractor.execute(state.id)
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Error getting balance for account ${state.id}", e)
                return null
            } catch (e: NumberFormatException) {
                Log.e(TAG, "Number format error calculating balance for account ${state.id}", e)
                return null
            }

        val adjustmentValue = newBalanceValue - currentBalance
        if (adjustmentValue == 0.0) return null

        // Format the adjustment value for display
        val formattedValue =
            if (adjustmentValue > 0) {
                "+R$ %.2f".format(adjustmentValue)
            } else {
                "R$ %.2f".format(adjustmentValue)
            }

        return Pair(formattedValue, adjustmentValue)
    }

    fun onAdjustBalanceConfirm(
        adjustmentValue: Double,
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        val state = _uiState.value
        if (!state.isEdit) {
            onError(R.string.detail_invalid_data)
            return
        }

        viewModelScope.launch {
            try {
                val adjustmentTransaction =
                    Transaction(
                        value = adjustmentValue,
                        description = "Balance adjustment",
                        date = domainTimeRepository.getCurrentDomainTime(),
                        accountId = state.id
                    )
                addTransactionRepository.save(adjustmentTransaction)
                onSuccess()
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Error saving balance adjustment transaction", e)
                onError(R.string.detail_invalid_data)
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Invalid argument for balance adjustment transaction", e)
                onError(R.string.detail_invalid_data)
            }
        }
    }
}

private fun AccountDetailUIState.toAccount() =
    Account(
        id = id,
        name = name.trim(),
        color = color.value.toLong(),
        archived = false
    )
