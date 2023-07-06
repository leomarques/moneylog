package lmm.moneylog.domain.transaction.gettransaction

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.transaction.Transaction

interface GetTransactionRepository {

    fun getTransactionById(id: Int): Flow<Transaction?>
}
