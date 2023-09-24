package lmm.moneylog.data.transaction.repositories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.transaction.Transaction

interface GetTransactionRepository {

    fun getTransactionById(id: Int): Flow<Transaction?>
}
