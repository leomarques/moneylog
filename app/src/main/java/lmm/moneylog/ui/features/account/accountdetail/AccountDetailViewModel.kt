package lmm.moneylog.ui.features.account.accountdetail

import androidx.compose.runtime.mutableStateOf
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
import lmm.moneylog.ui.features.transaction.transactiondetail.getIdParam

class AccountDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getAccountsRepository: GetAccountsRepository,
    private val addAccountRepository: AddAccountRepository,
    private val updateAccountRepository: UpdateAccountRepository,
    private val archiveAccountRepository: ArchiveAccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountDetailModel())
    val uiState: StateFlow<AccountDetailModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getAccountsRepository.getAccountById(id)?.let { account ->
                    _uiState.update {
                        AccountDetailModel(
                            name = mutableStateOf(account.name),
                            color = Color(account.color.toULong()),
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

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        val name = _uiState.value.name.value.trim()
        if (name.isEmpty()) {
            onError(R.string.detail_no_name)
        } else {
            _uiState.update { it.copy(showFab = false) }

            viewModelScope.launch {
                if (_uiState.value.isEdit) {
                    updateAccountRepository.update(_uiState.value.toAccount())
                } else {
                    addAccountRepository.save(_uiState.value.toAccount())
                }
                onSuccess()
            }
        }
    }

    fun onColorPicked(color: Color) {
        _uiState.update {
            it.copy(
                color = color
            )
        }
    }
}

fun AccountDetailModel.toAccount() =
    Account(
        id = id,
        name = name.value.trim(),
        color = color.value.toLong(),
        archived = false
    )
