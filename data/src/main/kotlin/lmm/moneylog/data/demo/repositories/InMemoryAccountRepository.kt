package lmm.moneylog.data.demo.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.AddAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.ArchiveAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.DeleteAccountRepository
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository
import lmm.moneylog.data.account.repositories.interfaces.UpdateAccountRepository

class InMemoryAccountRepository :
    GetAccountsRepository,
    AddAccountRepository,
    UpdateAccountRepository,
    DeleteAccountRepository,
    ArchiveAccountRepository {
    private val accounts = MutableStateFlow<List<Account>>(emptyList())
    private var nextId = 1

    override fun getAccounts(archived: Boolean): Flow<List<Account>> =
        accounts.map { list -> list.filter { it.archived == archived } }

    override suspend fun getAccountsSuspend(archived: Boolean): List<Account> =
        accounts.value.filter { it.archived == archived }

    override suspend fun getAccountById(id: Int): Account? =
        accounts.value.firstOrNull { it.id == id }

    override suspend fun save(account: Account) {
        val newAccount = account.copy(id = nextId++)
        accounts.value = accounts.value + newAccount
    }

    override suspend fun update(account: Account) {
        accounts.value =
            accounts.value.map {
                if (it.id == account.id) account else it
            }
    }

    override suspend fun delete(id: Int) {
        accounts.value = accounts.value.filterNot { it.id == id }
    }

    override suspend fun updateArchived(id: Int, archived: Boolean) {
        accounts.value =
            accounts.value.map {
                if (it.id == id) it.copy(archived = archived) else it
            }
    }

    internal fun clear() {
        accounts.value = emptyList()
        nextId = 1
    }

    internal fun setInitialData(data: List<Account>) {
        accounts.value = data
        nextId = (data.maxOfOrNull { it.id } ?: 0) + 1
    }
}
