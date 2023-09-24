package lmm.moneylog.data.balance

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.database.TransactionDao

class GetBalanceRepositoryImpl(private val transactionDao: TransactionDao) :
    GetBalanceRepository {

    override fun getAllTransactionsValues(): Flow<List<Double>> {
        return transactionDao.selectAllValues()
    }
}
