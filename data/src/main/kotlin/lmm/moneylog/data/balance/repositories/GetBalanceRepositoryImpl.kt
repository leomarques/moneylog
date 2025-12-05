package lmm.moneylog.data.balance.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.balance.model.TransactionBalance
import lmm.moneylog.data.transaction.database.TransactionDao

class GetBalanceRepositoryImpl(
    private val transactionDao: TransactionDao
) : GetBalanceRepository {
    override fun getTransactions(): Flow<List<TransactionBalance>> = transactionDao.selectTransactions()

    override suspend fun getAllValuesByAccount(accountId: Int): List<Double> =
        transactionDao.selectValuesByAccountId(accountId)

    override fun getAllValuesByAccountFlow(accountId: Int): Flow<List<Double>> =
        transactionDao.selectValuesByAccountIdFlow(accountId)
}
