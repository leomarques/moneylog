package lmm.moneylog.domain.transaction.edittransaction

import lmm.moneylog.domain.transaction.Transaction

class UpdateTransactionInteractor(private val updateTransactionRepository: UpdateTransactionRepository) {

    suspend fun execute(transaction: Transaction) {
        updateTransactionRepository.update(transaction)
    }
}
