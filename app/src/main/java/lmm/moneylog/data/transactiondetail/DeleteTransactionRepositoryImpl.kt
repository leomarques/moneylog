package lmm.moneylog.data.transactiondetail

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.transaction.TransactionDao
import lmm.moneylog.domain.deletetransaction.DeleteTransactionRepository

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
