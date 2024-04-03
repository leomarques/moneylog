package lmm.moneylog.data.balance.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.model.TransactionBalance

class GetBalanceRepositoryImpl(private val transactionDao: TransactionDao) :
    GetBalanceRepository {
    override fun getAllTransactionsValues(): Flow<List<TransactionBalance>> {
        return transactionDao.selectValuesFromAccounts()
    }

    override suspend fun getAllValuesByAccount(accountId: Int): List<Double> {
        return transactionDao.selectValuesByAccountId(accountId)
    }
}
