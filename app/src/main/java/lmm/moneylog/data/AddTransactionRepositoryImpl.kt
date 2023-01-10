package lmm.moneylog.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lmm.moneylog.domain.addtransaction.AddTransactionRepository

class AddTransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    AddTransactionRepository {

    override suspend fun save(value: Double) {
        withContext(Dispatchers.IO) {
            transactionDao.insert(TransactionEntity(value))
        }
    }
}
