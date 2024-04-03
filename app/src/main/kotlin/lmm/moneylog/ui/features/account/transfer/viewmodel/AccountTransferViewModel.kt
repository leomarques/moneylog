package lmm.moneylog.ui.features.account.transfer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.time.repositories.DomainTimeRepository
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.extensions.validateValue
import lmm.moneylog.ui.features.account.transfer.model.AccountTransferModel
import lmm.moneylog.ui.features.account.transfer.model.AccountTransferUIState

class AccountTransferViewModel(
    private val accountTransferRepository: AccountTransferRepository,
    private val getAccountsRepository: GetAccountsRepository,
    private val domainTimeRepository: DomainTimeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountTransferUIState())
    val uiState: StateFlow<AccountTransferUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val accounts = getAccountsRepository.getAccountsSuspend()

            _uiState.update {
                AccountTransferUIState(
                    accounts =
                        accounts.map {
                            AccountTransferModel(
                                id = it.id,
                                name = it.name,
                                color = it.color.toComposeColor()
                            )
                        },
                    date = domainTimeRepository.getCurrentDomainTime()
                )
            }
        }
    }

    fun onOriginAccountPicked(index: Int) {
        _uiState.update {
            it.copy(
                originAccountId = _uiState.value.accounts[index].id,
                originAccountDisplay = _uiState.value.accounts[index].name,
                originAccountColor = _uiState.value.accounts[index].color
            )
        }
    }

    fun onDestinationAccountPicked(index: Int) {
        _uiState.update {
            it.copy(
                destinationAccountId = _uiState.value.accounts[index].id,
                destinationAccountDisplay = _uiState.value.accounts[index].name,
                destinationAccountColor = _uiState.value.accounts[index].color
            )
        }
    }

    fun onValueChange(value: String) {
        _uiState.update { it.copy(value = value) }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        with(_uiState.value) {
            if (originAccountId == -1) {
                onError(R.string.transfer_error_no_origin)
                return
            }
            if (destinationAccountId == -1) {
                onError(R.string.transfer_error_no_destination)
                return
            }
            if (originAccountId == destinationAccountId) {
                onError(R.string.transfer_error_same_account)
                return
            }

            try {
                val finalValue = value.validateValue()

                viewModelScope.launch {
                    accountTransferRepository.transfer(
                        value = finalValue,
                        date = date,
                        originAccountId = originAccountId,
                        destinationAccountId = destinationAccountId
                    )

                    onSuccess()
                }
            } catch (e: NumberFormatException) {
                onError(R.string.detail_invalidvalue)
            }
        }
    }
}
