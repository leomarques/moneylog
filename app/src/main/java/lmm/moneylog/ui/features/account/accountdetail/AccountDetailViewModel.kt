package lmm.moneylog.ui.features.account.accountdetail

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.Account
import lmm.moneylog.data.account.repositories.AddAccountRepository
import lmm.moneylog.data.account.repositories.ArchivedAccountsRepository
import lmm.moneylog.data.account.repositories.GetAccountRepository
import lmm.moneylog.data.account.repositories.UpdateAccountRepository
import lmm.moneylog.ui.features.transaction.transactiondetail.getIdParam

class AccountDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getAccountRepository: GetAccountRepository,
    private val addAccountRepository: AddAccountRepository,
    private val updateAccountRepository: UpdateAccountRepository,
    private val archivedAccountsRepository: ArchivedAccountsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountDetailModel())
    val uiState: StateFlow<AccountDetailModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getAccountRepository.getAccountById(id)?.let { account ->
                    _uiState.update {
                        AccountDetailModel(
                            name = mutableStateOf(account.name),
                            color = mutableLongStateOf(account.color),
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
            archivedAccountsRepository.archive(_uiState.value.id)
        }
    }

    fun onFabClick() {
        viewModelScope.launch {
            if (_uiState.value.isEdit) {
                updateAccountRepository.update(_uiState.value.toAccount())
            } else {
                addAccountRepository.save(_uiState.value.toAccount())
            }
        }
    }
}

fun AccountDetailModel.toAccount() =
    Account(
        id = id,
        name = name.value,
        color = color.value,
        archived = false
    )
