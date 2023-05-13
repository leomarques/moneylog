package lmm.moneylog.data.repositories

import kotlinx.coroutines.flow.map
import lmm.moneylog.data.database.TransactionDao
import lmm.moneylog.data.database.TransactionEntity
import lmm.moneylog.domain.gettransactions.GetTransactionsRepository
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime

class GetTransactionsRepositoryImpl(private val transactionDao: TransactionDao) :
    GetTransactionsRepository {

    override fun getAllTransactions() =
        transactionDao.selectAllTransactions().map { transactionsList ->
            convertEntityToTransaction(transactionsList)
        }

    override fun getIncomeTransactions() =
        transactionDao.selectIncomeTransactions().map { transactionsList ->
            convertEntityToTransaction(transactionsList)
        }

    override fun getOutcomeTransactions() =
        transactionDao.selectOutcomeTransactions().map { transactionsList ->
            convertEntityToTransaction(transactionsList)
        }

    private fun convertEntityToTransaction(list: List<TransactionEntity>): List<Transaction> {
        return list.map {
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