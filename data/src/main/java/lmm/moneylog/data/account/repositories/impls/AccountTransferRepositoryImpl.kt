package lmm.moneylog.data.account.repositories.impls

import lmm.moneylog.data.account.repositories.AccountTransferRepository
import lmm.moneylog.data.transaction.database.TransactionDao
import lmm.moneylog.data.transaction.database.TransactionEntity
import lmm.moneylog.data.transaction.time.DomainTime

class AccountTransferRepositoryImpl(
    val transactionDao: TransactionDao
) : AccountTransferRepository {

    override suspend fun transfer(
        value: Double,
        date: DomainTime,
        originAccountId: Int,
        destinationAccountId: Int
    ) {
        transactionDao.insert(
            TransactionEntity(
                value = value,
                description = "transfer",
                year = date.year,
                month = date.month,
                day = date.day,
                accountId = destinationAccountId,
                categoryId = null,
                transfer = true
            )
        )
        transactionDao.insert(
            TransactionEntity(
                value = -value,
                description = "transfer",
                year = date.year,
                month = date.month,
                day = date.day,
                accountId = originAccountId,
                categoryId = null,
                transfer = true
            )
        )
    }
}
