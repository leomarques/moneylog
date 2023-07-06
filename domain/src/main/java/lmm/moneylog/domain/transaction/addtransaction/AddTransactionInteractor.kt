package lmm.moneylog.domain.transaction.addtransaction

import lmm.moneylog.domain.transaction.Transaction

class AddTransactionInteractor(private val addTransactionRepository: AddTransactionRepository) {
    suspend fun execute(transaction: Transaction) {
        addTransactionRepository.save(transaction)
    }
}
