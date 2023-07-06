package lmm.moneylog.data.account.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.domain.account.deleteaccount.DeleteAccountRepository

class DeleteAccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : DeleteAccountRepository {

    override suspend fun delete(id: Int) {
        withContext(coroutineDispatcherProvider.provide()) {
            accountDao.delete(id)
        }
    }
}
