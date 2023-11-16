package lmm.moneylog.ui.features.account.archive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.ArchiveAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.DeleteAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.ui.features.account.archive.model.ArchivedAccountModel

class ArchivedAccountsViewModel(
    private val getAccountsRepository: GetAccountsRepository,
    private val archiveAccountRepository: ArchiveAccountRepository,
    private val deleteAccountRepository: DeleteAccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(listOf<ArchivedAccountModel>())
    val uiState: StateFlow<List<ArchivedAccountModel>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAccountsRepository.getAccounts(true).collect { accounts ->
                _uiState.update {
                    accounts.reversed().map { it.toArchivedAccountModel() }
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

private fun Account.toArchivedAccountModel() =
    ArchivedAccountModel(
        id = id,
        name = name
    )
