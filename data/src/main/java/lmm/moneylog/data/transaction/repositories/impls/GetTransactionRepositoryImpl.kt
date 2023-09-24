package lmm.moneylog.data.transaction.repositories.impls

import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.repositories.GetTransactionRepository
import lmm.moneylog.data.transaction.time.DomainTime

class GetTransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    GetTransactionRepository {

    override suspend fun getTransactionById(id: Int): Transaction? {
        val transaction = transactionDao.selectTransaction(id)
        return if (transaction != null) {
            with(transaction) {
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
        } else {
            null
        }
    }
}
