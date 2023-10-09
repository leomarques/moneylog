package lmm.moneylog.ui.features.account.getaccounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.balance.GetBalanceByAccountInteractor

class GetAccountsViewModel(
    private val getAccountsRepository: GetAccountsRepository,
    private val accountTransferRepository: AccountTransferRepository,
    private val getBalanceByAccountInteractor: GetBalanceByAccountInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(GetAccountsModel())
    val uiState: StateFlow<GetAccountsModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAccountsRepository.getAccounts().collect { accounts ->
                val transfers = accountTransferRepository.getTransfers()
                val list = mutableListOf<AccountModel>()

                accounts.forEach { account ->
                    var balance = getBalanceByAccountInteractor.execute(account.id)

                    transfers.filter {
                        it.originAccountId == account.id
                    }.forEach {
                        balance -= it.value
                    }

                    transfers.filter {
                        it.destinationAccountId == account.id
                    }.forEach {
                        balance += it.value
                    }

                    list.add(
                        AccountModel(
                            id = account.id,
                            name = account.name,
                            balance = balance
                        )
                    )
                }

                _uiState.update {
                    GetAccountsModel(list)
                }
            }
        }
    }
}
