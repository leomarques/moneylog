package lmm.moneylog.domain.transaction.edittransaction

import lmm.moneylog.domain.transaction.Transaction

interface UpdateTransactionRepository {

    suspend fun update(transaction: Transaction)
}
