package lmm.moneylog.domain.transaction.gettransactions

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.transaction.Transaction

class GetTransactionsInteractor(private val repository: GetTransactionsRepository) {

    fun getAllTransactions(): Flow<List<Transaction>> {
        return repository.getAllTransactions()
    }

    fun getIncomeTransactions(): Flow<List<Transaction>> {
        return repository.getIncomeTransactions()
    }

    fun getOutcomeTransactions(): Flow<List<Transaction>> {
        return repository.getOutcomeTransactions()
    }
}
