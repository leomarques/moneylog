package lmm.moneylog.ui.features.account.getaccounts.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.repositories.GetAccountsRepository

class GetArchivedAccountsViewModel(
    private val getAccountsRepository: GetAccountsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GetArchivedAccountsModel())
    val uiState: StateFlow<GetArchivedAccountsModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val accounts = getAccountsRepository.getAccountsSuspend(true)

            _uiState.update {
                GetArchivedAccountsModel(
                    accounts.map {
                        ArchivedAccountModel(
                            id = it.id,
                            name = it.name
                        )
                    }
                )
            }
        }
    }
}
