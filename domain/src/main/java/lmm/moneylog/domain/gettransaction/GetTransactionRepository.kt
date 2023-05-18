package lmm.moneylog.domain.gettransaction

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Transaction

interface GetTransactionRepository {

    fun getTransactionById(id: Int): Flow<Transaction>
}
