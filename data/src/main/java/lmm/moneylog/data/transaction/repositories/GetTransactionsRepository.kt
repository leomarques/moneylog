package lmm.moneylog.data.transaction.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.Transaction

interface GetTransactionsRepository {

    fun getAllTransactions(): Flow<List<Transaction>>
    fun getIncomeTransactions(): Flow<List<Transaction>>
    fun getOutcomeTransactions(): Flow<List<Transaction>>
}
