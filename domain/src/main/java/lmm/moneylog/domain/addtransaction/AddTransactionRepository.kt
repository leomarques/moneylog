package lmm.moneylog.domain.addtransaction

import lmm.moneylog.domain.models.Transaction

interface AddTransactionRepository {
    suspend fun save(transaction: Transaction)
}
