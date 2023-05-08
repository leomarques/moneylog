package lmm.moneylog.domain.addtransaction

import lmm.moneylog.domain.addtransaction.model.Transaction

class AddTransactionInteractor(private val addTransactionRepository: AddTransactionRepository) {
    suspend fun execute(transaction: Transaction) {
        addTransactionRepository.save(transaction)
    }
}
