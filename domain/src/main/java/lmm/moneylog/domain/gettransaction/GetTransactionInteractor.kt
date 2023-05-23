package lmm.moneylog.domain.gettransaction

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Transaction

class GetTransactionInteractor(private val getTransactionRepository: GetTransactionRepository) {

    fun getTransaction(id: Int): Flow<Transaction?> {
        return getTransactionRepository.getTransactionById(id)
    }
}
