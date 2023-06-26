package lmm.moneylog.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.database.account.AccountDao
import lmm.moneylog.data.database.account.AccountEntity
import lmm.moneylog.domain.getaccounts.GetAccountsRepository
import lmm.moneylog.domain.models.Account

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
