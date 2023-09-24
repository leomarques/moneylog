package lmm.moneylog.ui.features.account.getaccounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.repositories.GetAccountsRepository

class GetAccountsViewModel(private val getAccountsRepository: GetAccountsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(GetAccountsModel())
    val uiState: StateFlow<GetAccountsModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAccountsRepository.getAccounts().collect { accounts ->
                _uiState.update {
                    GetAccountsModel(accounts)
                }
            }
        }
    }
}
