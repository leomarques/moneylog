package lmm.moneylog.data.transaction.repositories.impls

import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.model.Transaction
import lmm.moneylog.data.transaction.repositories.interfaces.UpdateTransactionRepository

class UpdateTransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : UpdateTransactionRepository {
    override suspend fun update(transaction: Transaction) {
        transactionDao.update(
            with(transaction) {
                TransactionEntity(
                    value = value,
                    description = description,
                    year = date.year,
                    month = date.month,
                    day = date.day,
                    accountId = accountId,
                    categoryId = categoryId,
                    creditCardId = creditCardId,
                    invoiceMonth = invoiceMonth,
                    invoiceYear = invoiceYear,
                ).also {
                    it.id = id
                }
            }
        )
    }

    override suspend fun payInvoice(
        transactions: List<Transaction>,
        paramAccountId: Int,
        paramDate: DomainTime
    ) {
        transactionDao.update(
            transactionEntityList =
                transactions.map { transaction ->
                    with(transaction) {
                        TransactionEntity(
                            value = value,
                            description = description,
                            year = date.year,
                            month = date.month,
                            day = date.day,
                            categoryId = categoryId,
                            creditCardId = creditCardId,
                            invoiceMonth = invoiceMonth,
                            invoiceYear = invoiceYear,
                            paidDay = paramDate.day,
                            paidMonth = paramDate.month,
                            paidYear = paramDate.year,
                            accountId = paramAccountId,
                        ).also {
                            it.id = id
                        }
                    }
                }
        )
    }
}
