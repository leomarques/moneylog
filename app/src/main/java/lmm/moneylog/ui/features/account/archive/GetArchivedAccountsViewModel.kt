package lmm.moneylog.ui.features.account.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.repositories.ArchivedAccountsRepository
import lmm.moneylog.data.account.repositories.DeleteAccountRepository
import lmm.moneylog.data.account.repositories.GetAccountsRepository

class GetArchivedAccountsViewModel(
    private val getAccountsRepository: GetAccountsRepository,
    private val archivedAccountsRepository: ArchivedAccountsRepository,
    private val deleteAccountRepository: DeleteAccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GetArchivedAccountsModel())
    val uiState: StateFlow<GetArchivedAccountsModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAccountsRepository.getAccounts(true).collect { accounts ->
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

    fun unarchive(id: Int) {
        viewModelScope.launch {
            archivedAccountsRepository.unarchive(id)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            deleteAccountRepository.delete(id)
        }
    }
}
