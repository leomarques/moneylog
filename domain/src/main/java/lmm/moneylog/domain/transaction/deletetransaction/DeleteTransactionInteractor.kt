package lmm.moneylog.domain.transaction.deletetransaction

class DeleteTransactionInteractor(
    private val deleteTransactionRepository: DeleteTransactionRepository
) {
    suspend fun execute(id: Int) {
        deleteTransactionRepository.delete(id)
    }
}
