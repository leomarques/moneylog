package lmm.moneylog.data.transaction.repositories.impls

import kotlinx.coroutines.withContext
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.repositories.DeleteTransactionRepository

class DeleteTransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : DeleteTransactionRepository {

    override suspend fun delete(id: Int) {
        withContext(coroutineDispatcherProvider.provide()) {
            transactionDao.delete(id)
        }
    }
}
