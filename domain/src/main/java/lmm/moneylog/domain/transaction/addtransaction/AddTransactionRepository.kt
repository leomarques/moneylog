package lmm.moneylog.domain.transaction.addtransaction

import lmm.moneylog.domain.transaction.Transaction

interface AddTransactionRepository {
    suspend fun save(transaction: Transaction)
}
