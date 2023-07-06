package lmm.moneylog.data.transaction.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.domain.time.DomainTime
import lmm.moneylog.domain.transaction.Transaction
import lmm.moneylog.domain.transaction.gettransaction.GetTransactionRepository

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
