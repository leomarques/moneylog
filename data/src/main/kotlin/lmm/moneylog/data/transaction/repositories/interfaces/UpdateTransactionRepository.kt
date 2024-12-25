package lmm.moneylog.data.transaction.repositories.interfaces

import lmm.moneylog.data.time.model.DomainTime
import lmm.moneylog.data.transaction.model.Transaction

interface UpdateTransactionRepository {
    suspend fun update(transaction: Transaction)

    suspend fun payInvoice(
        transactions: List<Transaction>,
        paramAccountId: Int,
        paramDate: DomainTime
    )
}
