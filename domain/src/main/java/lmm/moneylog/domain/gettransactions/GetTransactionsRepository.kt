package lmm.moneylog.domain.gettransactions

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Transaction

interface GetTransactionsRepository {

    fun getTransactions(): Flow<List<Transaction>>
}
