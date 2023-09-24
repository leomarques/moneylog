package lmm.moneylog.data.transaction.repositories.impls

import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.repositories.AddTransactionRepository

class AddTransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : AddTransactionRepository {

    override suspend fun save(transaction: Transaction) {
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
