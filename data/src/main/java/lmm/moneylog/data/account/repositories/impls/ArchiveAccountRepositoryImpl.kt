package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.repositories.ArchiveAccountRepository

class ArchiveAccountRepositoryImpl(
    private val accountDao: AccountDao
) : ArchiveAccountRepository {

    override suspend fun archive(id: Int) {
        accountDao.archive(id, true)
    }
}
