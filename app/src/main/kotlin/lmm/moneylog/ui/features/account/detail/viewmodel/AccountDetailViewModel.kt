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
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.AddAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.ArchiveAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.account.repositories.interfaces.UpdateAccountRepository
import lmm.moneylog.ui.extensions.getIdParam
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.account.detail.model.AccountDetailUIState

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
                            id = account.id,
                            name = account.name,
                            color = account.color.toComposeColor(),
                            isEdit = true
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
        _uiState.update { it.copy(name = name.trim()) }
    }

    fun onColorPick(color: Color) {
        _uiState.update { it.copy(color = color) }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        val state = _uiState.value
        if (state.name.isEmpty()) {
            onError(R.string.detail_no_name)
            return
        }

        _uiState.update { it.copy(showFab = false) }

        viewModelScope.launch {
            if (state.isEdit) {
                updateAccountRepository.update(state.toAccount())
            } else {
                addAccountRepository.save(state.toAccount())
            }

            onSuccess()
        }
    }
}

private fun AccountDetailUIState.toAccount() =
    Account(
        id = id,
        name = name,
        color = color.value.toLong(),
        archived = false
    )
