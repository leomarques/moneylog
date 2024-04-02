package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.repositories.interfaces.DeleteAccountRepository

class DeleteAccountRepositoryImpl(
    private val accountDao: AccountDao
) : DeleteAccountRepository {

    override suspend fun delete(id: Int) {
        accountDao.delete(id)
    }
}
