package lmm.moneylog.data.transaction.repositories.impls

import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.repositories.interfaces.DeleteTransactionRepository

class DeleteTransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : DeleteTransactionRepository {
    override suspend fun delete(id: Int) {
        transactionDao.delete(id)
    }
}
