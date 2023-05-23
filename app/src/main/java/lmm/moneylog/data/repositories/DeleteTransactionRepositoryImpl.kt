package lmm.moneylog.data.repositories

import lmm.moneylog.data.database.TransactionDao
import lmm.moneylog.domain.deletetransaction.DeleteTransactionRepository

class DeleteTransactionRepositoryImpl(private val dao: TransactionDao) :
    DeleteTransactionRepository {
    override suspend fun delete(id: Int) {
        dao.delete(id)
    }
}
