package lmm.moneylog.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {

    override suspend fun save(value: Double) {
        withContext(Dispatchers.IO) {
            transactionDao.insert(TransactionEntity(value))
        }
    }

    override fun get(): LiveData<List<TransactionEntity>> {
        return transactionDao.selectAll()
    }
}
