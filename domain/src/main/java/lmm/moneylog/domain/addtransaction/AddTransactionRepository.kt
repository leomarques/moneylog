package lmm.moneylog.domain.addtransaction

import lmm.moneylog.domain.addtransaction.model.Transaction

interface AddTransactionRepository {
    suspend fun save(transaction: Transaction)
}
