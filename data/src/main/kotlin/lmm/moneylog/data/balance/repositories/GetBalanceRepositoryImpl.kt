package lmm.moneylog.data.balance.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.balance.model.TransactionBalance
import lmm.moneylog.data.transaction.database.TransactionDao

class GetBalanceRepositoryImpl(private val transactionDao: TransactionDao) :
    GetBalanceRepository {
    override fun getTransactions(): Flow<List<TransactionBalance>> {
        return transactionDao.selectTransactions()
    }

    override suspend fun getAllValuesByAccount(accountId: Int): List<Double> {
        return transactionDao.selectValuesByAccountId(accountId)
    }
}
