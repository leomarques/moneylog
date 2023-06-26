package lmm.moneylog.data.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.account.AccountDao
import lmm.moneylog.domain.deleteaccount.DeleteAccountRepository

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
