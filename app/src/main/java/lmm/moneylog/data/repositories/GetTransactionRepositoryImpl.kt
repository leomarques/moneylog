package lmm.moneylog.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.database.TransactionDao
import lmm.moneylog.domain.gettransaction.GetTransactionRepository
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.domain.time.DomainTime

class GetTransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    GetTransactionRepository {

    override fun getTransactionById(id: Int): Flow<Transaction> {
        return transactionDao.selectTransaction(id).map {
            Transaction(
                value = it.value,
                description = it.description,
                date = DomainTime(
                    day = it.day,
                    month = it.month,
                    year = it.year
                )
            )
        }
    }
}
