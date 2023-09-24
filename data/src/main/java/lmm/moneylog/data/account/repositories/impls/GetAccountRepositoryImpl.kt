package lmm.moneylog.data.account.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.account.Account
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.repositories.GetAccountRepository

class GetAccountRepositoryImpl(private val accountDao: AccountDao) :
    GetAccountRepository {

    override fun getAccountById(id: Int): Flow<Account?> {
        return accountDao.selectAccount(id).map { account ->
            if (account != null) {
                with(account) {
                    Account(
                        id = id,
                        name = name,
                        color = color
                    )
                }
            } else {
                null
            }
        }
    }
}
