package lmm.moneylog.domain.gettransactions

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.addtransaction.model.Transaction

class GetTransactionsInteractor(private val repository: GetTransactionsRepository) {

    fun execute(): Flow<List<Transaction>> {
        return repository.getTransactions()
    }
}
