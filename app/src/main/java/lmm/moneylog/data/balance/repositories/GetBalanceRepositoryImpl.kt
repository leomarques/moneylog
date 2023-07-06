package lmm.moneylog.data.balance.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.domain.balance.getbalance.GetBalanceRepository

class GetBalanceRepositoryImpl(private val transactionDao: TransactionDao) :
    GetBalanceRepository {

    override fun getAllTransactionsValues(): Flow<List<Double>> {
        return transactionDao.selectAllValues()
    }
}
