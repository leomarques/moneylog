package lmm.moneylog.domain.gettransactions

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.addtransaction.model.Transaction

interface GetTransactionsRepository {

    fun getTransactions(): Flow<List<Transaction>>
}
