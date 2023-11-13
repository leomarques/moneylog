package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.repositories.interfaces.ArchiveAccountRepository

class ArchiveAccountRepositoryImpl(
    private val accountDao: AccountDao
) : ArchiveAccountRepository {

    override suspend fun updateArchived(
        id: Int,
        archived: Boolean
    ) {
        accountDao.updateArchived(
            id = id,
            archived = archived
        )
    }
}
