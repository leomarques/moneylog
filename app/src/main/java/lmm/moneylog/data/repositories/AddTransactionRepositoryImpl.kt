package lmm.moneylog.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lmm.moneylog.data.TransactionDao
import lmm.moneylog.data.TransactionEntity
import lmm.moneylog.domain.addtransaction.AddTransactionRepository
import lmm.moneylog.domain.addtransaction.model.Transaction

class AddTransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    AddTransactionRepository {

    override suspend fun save(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            transactionDao.insert(
                with(transaction) {
                    TransactionEntity(
                        value = value,
                        description = description,
                        year = date.year,
                        month = date.month,
                        day = date.day,
                        hour = date.hour,
                        minute = date.minute
                    )
                }
            )
        }
    }
}
