package lmm.moneylog.data.transaction.repositories.impls

import kotlinx.coroutines.flow.map
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.repositories.GetTransactionsRepository
import lmm.moneylog.data.transaction.time.DomainTime

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
        return list.map { entity ->
            with(entity) {
                Transaction(
                    id = id,
                    value = value,
                    description = description,
                    date = DomainTime(
                        day = day,
                        month = month,
                        year = year
                    )
                )
            }
        }
    }
}
