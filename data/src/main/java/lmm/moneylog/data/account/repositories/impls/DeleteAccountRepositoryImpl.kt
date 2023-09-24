package lmm.moneylog.data.account.repositories.impls

import kotlinx.coroutines.withContext
import lmm.moneylog.data.account.database.AccountDao
import lmm.moneylog.data.account.repositories.DeleteAccountRepository
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider

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
