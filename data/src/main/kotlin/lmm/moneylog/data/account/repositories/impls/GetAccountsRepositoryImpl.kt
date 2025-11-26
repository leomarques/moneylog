package lmm.moneylog.data.account.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.account.model.Account
import lmm.moneylog.data.account.repositories.interfaces.GetAccountsRepository

class GetAccountsRepositoryImpl(
    private val accountDao: AccountDao
) : GetAccountsRepository {
    override suspend fun getAccountById(id: Int): Account? {
        val account = accountDao.selectAccountById(id)
        return if (account != null) {
            with(account) {
                Account(
                    id = id,
                    name = name,
                    color = color,
                    archived = archived
                )
            }
        } else {
            null
        }
    }

    override fun getAccounts(archived: Boolean): Flow<List<Account>> =
        accountDao
            .selectAccounts()
            .map { accountsList ->
                convertEntity(
                    accountsList.filter {
                        it.archived == archived
                    }
                )
            }

    override suspend fun getAccountsSuspend(archived: Boolean): List<Account> =
        accountDao
            .selectAccountsSuspend()
            .filter { it.archived == archived }
            .map { account ->
                with(account) {
                    Account(
                        id = id,
                        name = name,
                        color = color,
                        archived = archived
                    )
                }
            }

    private fun convertEntity(list: List<AccountEntity>): List<Account> =
        list.map { account ->
            with(account) {
                Account(
                    id = id,
                    name = name,
                    color = color,
                    archived = archived
                )
            }
        }
}
