package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.Account
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.repositories.GetAccountRepository

class GetAccountRepositoryImpl(private val accountDao: AccountDao) :
    GetAccountRepository {

    override suspend fun getAccountById(id: Int): Account? {
        val account = accountDao.selectAccount(id)
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
}
