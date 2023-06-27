package lmm.moneylog.data.transactiondetail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.database.transaction.TransactionDao
import lmm.moneylog.domain.gettransaction.GetTransactionRepository
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime

class GetTransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    GetTransactionRepository {

    override fun getTransactionById(id: Int): Flow<Transaction?> {
        return transactionDao.selectTransaction(id).map { entity ->
            if (entity != null) {
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
            } else {
                null
            }
        }
    }
}
