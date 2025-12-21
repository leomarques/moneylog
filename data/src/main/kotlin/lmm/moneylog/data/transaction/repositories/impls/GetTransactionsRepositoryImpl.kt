package lmm.moneylog.data.transaction.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.GetTransactionsRepository

class GetTransactionsRepositoryImpl(
    private val transactionDao: TransactionDao
) : GetTransactionsRepository {
    override suspend fun getTransactionById(id: Long): Transaction? {
        val transaction = transactionDao.selectTransactionById(id)
        return if (transaction != null) {
            with(transaction) {
                Transaction(
                    id = id,
                    value = value,
                    description = description,
                    date =
                        DomainTime(
                            day = day,
                            month = month,
                            year = year
                        ),
                    accountId = accountId,
                    categoryId = categoryId,
                    creditCardId = creditCardId,
                    invoiceMonth = invoiceMonth,
                    invoiceYear = invoiceYear
                )
            }
        } else {
            null
        }
    }

    override fun getAllTransactions(
        month: Int,
        year: Int
    ) = transactionDao
        .selectAllTransactions(
            month = month,
            year = year
        ).map { transactionsList ->
            convertEntityToTransaction(transactionsList)
        }

    override fun getIncomeTransactions(
        month: Int,
        year: Int
    ) = transactionDao
        .selectIncomeTransactions(
            month = month,
            year = year
        ).map { transactionsList ->
            convertEntityToTransaction(transactionsList)
        }

    override fun getOutcomeTransactions(
        month: Int,
        year: Int
    ) = transactionDao
        .selectOutcomeTransactions(
            month = month,
            year = year
        ).map { transactionsList ->
            convertEntityToTransaction(transactionsList)
        }

    override fun getTransactionsByInvoice(
        invoiceCode: String,
        creditCardId: Int
    ): Flow<List<Transaction>> {
        val split = invoiceCode.split(".")
        val invoiceMonth = split[0].toInt()
        val invoiceYear = split[1].toInt()

        return transactionDao
            .selectTransactionByInvoice(
                invoiceMonth = invoiceMonth,
                invoiceYear = invoiceYear,
                creditCardId = creditCardId
            ).map { transactionsList ->
                convertEntityToTransaction(transactionsList)
            }
    }

    private fun convertEntityToTransaction(list: List<TransactionEntity>): List<Transaction> =
        list.map { entity ->
            with(entity) {
                Transaction(
                    id = id,
                    value = value,
                    description = description,
                    date =
                        DomainTime(
                            day = day,
                            month = month,
                            year = year
                        ),
                    accountId = accountId,
                    categoryId = categoryId,
                    creditCardId = creditCardId,
                    invoiceMonth = invoiceMonth,
                    invoiceYear = invoiceYear
                )
            }
        }
}
