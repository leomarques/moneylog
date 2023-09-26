package lmm.moneylog.ui.features.account.accountdetail

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lmm.moneylog.data.account.Account
import lmm.moneylog.data.account.repositories.AddAccountRepository
import lmm.moneylog.data.account.repositories.DeleteAccountRepository
import lmm.moneylog.data.account.repositories.GetAccountRepository
import lmm.moneylog.data.account.repositories.UpdateAccountRepository
import lmm.moneylog.ui.features.transaction.transactiondetail.getIdParam

class AccountDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getAccountRepository: GetAccountRepository,
    private val addAccountRepository: AddAccountRepository,
    private val updateAccountRepository: UpdateAccountRepository,
    private val deleteAccountRepository: DeleteAccountRepository
) : ViewModel() {

    var model = AccountDetailModel()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getAccountRepository.getAccountById(id)?.let { account ->
                    model = AccountDetailModel(
                        name = mutableStateOf(account.name),
                        isEdit = true,
                        id = account.id,
                        color = mutableLongStateOf(account.color)
                    )
                }
            }
        }
    }

    fun deleteAccount(id: Int) {
        viewModelScope.launch {
            deleteAccountRepository.delete(id)
        }
    }

    fun onFabClick() {
        viewModelScope.launch {
            if (model.isEdit) {
                updateAccountRepository.update(model.toAccount())
            } else {
                addAccountRepository.save(model.toAccount())
            }
        }
    }
}

fun AccountDetailModel.toAccount() =
    Account(
        id = id,
        name = name.value,
        color = color.value
    )
