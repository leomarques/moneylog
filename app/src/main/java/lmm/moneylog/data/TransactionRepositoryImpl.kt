package lmm.moneylog.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {

    override suspend fun save(value: Double) {
        withContext(Dispatchers.IO) {
            transactionDao.insert(TransactionEntity(value))
        }
    }

    override fun get(): Flow<List<TransactionEntity>> {
        return transactionDao.selectAll()
    }
}
