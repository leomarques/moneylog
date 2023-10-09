package lmm.moneylog.ui.features.account.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.repositories.ArchiveAccountRepository
import lmm.moneylog.data.account.repositories.DeleteAccountRepository
import lmm.moneylog.data.account.repositories.GetAccountsRepository

class GetArchivedAccountsViewModel(
    private val getAccountsRepository: GetAccountsRepository,
    private val archiveAccountRepository: ArchiveAccountRepository,
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
            archiveAccountRepository.updateArchived(
                id = id,
                archived = false
            )
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            deleteAccountRepository.delete(id)
        }
    }
}
