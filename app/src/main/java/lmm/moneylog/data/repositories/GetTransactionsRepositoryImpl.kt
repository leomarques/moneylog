package lmm.moneylog.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.database.TransactionDao
import lmm.moneylog.domain.gettransactions.GetTransactionsRepository
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime

class GetTransactionsRepositoryImpl(private val transactionDao: TransactionDao) :
    GetTransactionsRepository {

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.selectAllTransactions().map { transactionsList ->
            transactionsList.map {
                Transaction(
                    it.value,
                    it.description,
                    DomainTime(
                        it.day,
                        it.month,
                        it.year
                    )
                )
            }
        }
    }
}
