package lmm.moneylog.domain.edittransaction

import lmm.moneylog.domain.models.Transaction

class UpdateTransactionInteractor(private val updateTransactionRepository: UpdateTransactionRepository) {

    suspend fun execute(transaction: Transaction) {
        updateTransactionRepository.update(transaction)
    }
}
