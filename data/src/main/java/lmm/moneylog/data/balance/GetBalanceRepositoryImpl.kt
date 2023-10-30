package lmm.moneylog.data.balance

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.database.TransactionBalance
import lmm.moneylog.data.transaction.database.TransactionDao

class GetBalanceRepositoryImpl(private val transactionDao: TransactionDao) :
    GetBalanceRepository {

    override fun getAllTransactionsValues(): Flow<List<TransactionBalance>> {
        return transactionDao.selectValuesFromAccounts()
    }

    override suspend fun getAllValuesByAccount(accountId: Int): List<Double> {
        return transactionDao.selectValuesByAccountId(accountId)
    }
}
