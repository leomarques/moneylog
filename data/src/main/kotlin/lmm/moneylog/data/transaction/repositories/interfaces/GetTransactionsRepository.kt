package lmm.moneylog.data.transaction.repositories.interfaces

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.model.Transaction

interface GetTransactionsRepository {
    suspend fun getTransactionById(id: Int): Transaction?

    fun getAllTransactions(): Flow<List<Transaction>>

    fun getIncomeTransactions(): Flow<List<Transaction>>

    fun getOutcomeTransactions(): Flow<List<Transaction>>

    fun getTransactionsByInvoice(
        invoiceCode: String,
        creditCardId: Int
    ): Flow<List<Transaction>>
}
