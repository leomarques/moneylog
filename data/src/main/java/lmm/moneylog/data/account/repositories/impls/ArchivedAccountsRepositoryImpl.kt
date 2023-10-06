package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.repositories.ArchivedAccountsRepository

class ArchivedAccountsRepositoryImpl(
    private val accountDao: AccountDao
) : ArchivedAccountsRepository {

    override suspend fun archive(id: Int) {
        accountDao.archive(id, true)
    }

    override suspend fun unarchive(id: Int) {
        accountDao.archive(id, false)
    }
}
