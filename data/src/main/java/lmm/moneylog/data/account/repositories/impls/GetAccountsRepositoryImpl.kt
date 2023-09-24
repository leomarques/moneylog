package lmm.moneylog.data.account.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.account.Account
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.account.repositories.GetAccountsRepository

class GetAccountsRepositoryImpl(private val accountDao: AccountDao) :
    GetAccountsRepository {

    override fun getAccounts(): Flow<List<Account>> {
        return accountDao.selectAccounts().map { accountsList ->
            convertEntity(accountsList)
        }
    }

    private fun convertEntity(list: List<AccountEntity>): List<Account> {
        return list.map { account ->
            with(account) {
                Account(
                    id = id,
                    name = name,
                    color = color
                )
            }
        }
    }
}
