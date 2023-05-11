package lmm.moneylog.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.TransactionDao
import lmm.moneylog.domain.addtransaction.model.Transaction
import lmm.moneylog.domain.addtransaction.time.DomainTime
import lmm.moneylog.domain.gettransactions.GetTransactionsRepository

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
