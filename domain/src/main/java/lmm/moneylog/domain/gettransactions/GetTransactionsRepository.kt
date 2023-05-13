package lmm.moneylog.domain.gettransactions

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Transaction

interface GetTransactionsRepository {

    fun getAllTransactions(): Flow<List<Transaction>>
    fun getIncomeTransactions(): Flow<List<Transaction>>
    fun getOutcomeTransactions(): Flow<List<Transaction>>
}
