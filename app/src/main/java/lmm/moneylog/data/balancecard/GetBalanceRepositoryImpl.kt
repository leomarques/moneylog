package lmm.moneylog.data.balancecard

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.database.transaction.TransactionDao
import lmm.moneylog.domain.getbalance.GetBalanceRepository

class GetBalanceRepositoryImpl(private val transactionDao: TransactionDao) :
    GetBalanceRepository {

    override fun getAllTransactionsValues(): Flow<List<Double>> {
        return transactionDao.selectAllValues()
    }
}
