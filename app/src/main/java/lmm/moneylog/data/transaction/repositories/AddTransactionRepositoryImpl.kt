package lmm.moneylog.data.transaction.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.domain.transaction.Transaction
import lmm.moneylog.domain.transaction.addtransaction.AddTransactionRepository

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
