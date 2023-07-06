package lmm.moneylog.domain.transaction.gettransaction

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.transaction.Transaction

class GetTransactionInteractor(private val getTransactionRepository: GetTransactionRepository) {

    fun getTransaction(id: Int): Flow<Transaction?> {
        return getTransactionRepository.getTransactionById(id)
    }
}
