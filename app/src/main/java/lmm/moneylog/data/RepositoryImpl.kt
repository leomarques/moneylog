package lmm.moneylog.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RepositoryImpl(private val transactionDao: TransactionDao) : Repository {

    override suspend fun save(value: Double) {
        withContext(Dispatchers.IO) {
            transactionDao.insert(Transaction(value, ""))
        }
    }

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAll()
    }
}
