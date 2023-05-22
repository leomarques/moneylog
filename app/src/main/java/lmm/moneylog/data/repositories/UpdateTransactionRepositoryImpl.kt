package lmm.moneylog.data.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.TransactionDao
import lmm.moneylog.data.database.TransactionEntity
import lmm.moneylog.domain.edittransaction.UpdateTransactionRepository
import lmm.moneylog.domain.models.Transaction

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
