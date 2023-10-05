package lmm.moneylog.ui.features.account.getaccounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.balance.GetBalanceByAccountInteractor
import lmm.moneylog.ui.textformatters.formatForRs

class GetAccountsViewModel(
    private val getAccountsRepository: GetAccountsRepository,
    private val getBalanceByAccountInteractor: GetBalanceByAccountInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(GetAccountsModel())
    val uiState: StateFlow<GetAccountsModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAccountsRepository.getAccounts().collect { accounts ->
                val list = mutableListOf<AccountModel>()
                accounts.forEach { account ->
                    val balance = getBalanceByAccountInteractor.execute(account.id)

                    list.add(
                        AccountModel(
                            account = account,
                            balance = balance.formatForRs()
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
