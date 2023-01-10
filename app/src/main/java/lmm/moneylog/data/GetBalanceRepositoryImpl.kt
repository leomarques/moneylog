package lmm.moneylog.data

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.getbalance.GetBalanceRepository

class GetBalanceRepositoryImpl(private val transactionDao: TransactionDao) :
    GetBalanceRepository {

    override fun getAllTransactionsValues(): Flow<List<Double>> {
        return transactionDao.selectAllValues()
    }
}
