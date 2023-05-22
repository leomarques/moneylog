package lmm.moneylog.domain.edittransaction

import lmm.moneylog.domain.models.Transaction

interface UpdateTransactionRepository {

    suspend fun update(transaction: Transaction)
}
