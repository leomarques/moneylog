package lmm.moneylog.ui.features.account.detail.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.account.Account
import lmm.moneylog.data.account.repositories.AddAccountRepository
import lmm.moneylog.data.account.repositories.ArchiveAccountRepository
import lmm.moneylog.data.account.repositories.GetAccountsRepository
import lmm.moneylog.data.account.repositories.UpdateAccountRepository
import lmm.moneylog.ui.features.account.detail.model.AccountDetailUIState
import lmm.moneylog.ui.features.toColor
import lmm.moneylog.ui.features.transaction.transactiondetail.getIdParam

class AccountDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getAccountsRepository: GetAccountsRepository,
    private val addAccountRepository: AddAccountRepository,
    private val updateAccountRepository: UpdateAccountRepository,
    private val archiveAccountRepository: ArchiveAccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountDetailUIState())
    val uiState: StateFlow<AccountDetailUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getAccountsRepository.getAccountById(id)?.let { account ->
                    _uiState.update {
                        AccountDetailUIState(
                            name = account.name,
                            color = account.color.toColor(),
                            isEdit = true,
                            id = account.id
                        )
                    }
                }
            }
        }
    }

    fun archiveAccount() {
        viewModelScope.launch {
            archiveAccountRepository.updateArchived(
                id = _uiState.value.id,
                archived = true
            )
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onColorPick(color: Color) {
        _uiState.update { it.copy(color = color) }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        val state = _uiState.value
        val name = state.name.trim()
        val isEdit = state.isEdit

        if (name.isEmpty()) {
            onError(R.string.detail_no_name)
        } else {
            _uiState.update { it.copy(showFab = false) }

            viewModelScope.launch {
                if (isEdit) {
                    updateAccountRepository.update(state.toAccount())
                } else {
                    addAccountRepository.save(state.toAccount())
                }
            }

            onSuccess()
        }
    }
}

private fun AccountDetailUIState.toAccount() =
    Account(
        id = id,
        name = name.trim(),
        color = color.value.toLong(),
        archived = false
    )
