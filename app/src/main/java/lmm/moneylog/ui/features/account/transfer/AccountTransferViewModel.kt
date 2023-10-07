package lmm.moneylog.ui.features.account.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.repositories.AccountTransferRepository
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.transaction.time.DomainTimeConverter
import lmm.moneylog.ui.features.transaction.transactiondetail.validateValue

class AccountTransferViewModel(
    private val accountTransferRepository: AccountTransferRepository,
    private val getAccountsRepository: GetAccountsRepository,
    private val domainTimeConverter: DomainTimeConverter
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountTransferModel())
    val uiState: StateFlow<AccountTransferModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val accounts = getAccountsRepository.getAccountsSuspend()

            _uiState.update {
                AccountTransferModel(
                    accounts = accounts.map {
                        it.id to it.name
                    },
                    date = domainTimeConverter.getCurrentDomainTime()
                )
            }
        }
    }

    fun onOriginAccountPicked(index: Int) {
        _uiState.update {
            it.copy(
                originAccountId = _uiState.value.accounts[index].first,
                originAccountDisplay = _uiState.value.accounts[index].second
            )
        }
    }

    fun onDestinationAccountPicked(index: Int) {
        _uiState.update {
            it.copy(
                destinationAccountId = _uiState.value.accounts[index].first,
                destinationAccountDisplay = _uiState.value.accounts[index].second
            )
        }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        with(_uiState.value) {
            if (originAccountId == -1 ||
                destinationAccountId == -1 ||
                originAccountId == destinationAccountId
            ) {
                onError(R.string.detailtransaction_no_account)
            } else {
                try {
                    val finalValue = value.value.validateValue()

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
                    onError(R.string.detailtransaction_invalidvalue)
                }
            }
        }
    }
}
