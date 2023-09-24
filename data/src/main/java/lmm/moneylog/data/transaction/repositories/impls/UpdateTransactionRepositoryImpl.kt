package lmm.moneylog.data.transaction.repositories.impls

import kotlinx.coroutines.withContext
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.repositories.UpdateTransactionRepository

class UpdateTransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) :
    UpdateTransactionRepository {

    override suspend fun update(transaction: Transaction) {
        withContext(coroutineDispatcherProvider.provide()) {
            transactionDao.update(
                with(transaction) {
                    TransactionEntity(
                        value = value,
                        description = description,
                        year = date.year,
                        month = date.month,
                        day = date.day
                    ).also {
                        it.id = id
                    }
                }
            )
        }
    }
}
