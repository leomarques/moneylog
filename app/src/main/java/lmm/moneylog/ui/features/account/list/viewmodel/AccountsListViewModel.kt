package lmm.moneylog.ui.features.account.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.accounttransfer.repositories.AccountTransferRepository
import lmm.moneylog.data.balance.GetBalanceByAccountInteractor
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.account.list.model.AccountModel
import lmm.moneylog.ui.features.account.list.model.AccountsListUIState

class AccountsListViewModel(
    getAccountsRepository: GetAccountsRepository,
    accountTransferRepository: AccountTransferRepository,
    private val getBalanceByAccountInteractor: GetBalanceByAccountInteractor
) : ViewModel() {

    private val accountsFlow = getAccountsRepository.getAccounts()
    private val transfersFlow = accountTransferRepository.getTransfers()

    var uiState = accountsFlow.combine(transfersFlow) { accounts, transfers ->
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
                    balance = balance.formatForRs(),
                    color = account.color.toComposeColor()
                )
            )
        }

        AccountsListUIState(list.reversed())
    }.stateIn(viewModelScope, SharingStarted.Lazily, AccountsListUIState())
}
