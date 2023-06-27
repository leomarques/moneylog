package lmm.moneylog.data.transactiondetail

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.transaction.TransactionDao
import lmm.moneylog.data.database.transaction.TransactionEntity
import lmm.moneylog.domain.addtransaction.AddTransactionRepository
import lmm.moneylog.domain.models.Transaction

class AddTransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : AddTransactionRepository {

    override suspend fun save(transaction: Transaction) {
        withContext(coroutineDispatcherProvider.provide()) {
            transactionDao.insert(
                with(transaction) {
                    TransactionEntity(
                        value = value,
                        description = description,
                        year = date.year,
                        month = date.month,
                        day = date.day
                    )
                }
            )
        }
    }
}
