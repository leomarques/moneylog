package lmm.moneylog.domain.addtransaction

class AddTransactionInteractor(private val addTransactionRepository: AddTransactionRepository) {
    suspend fun execute(value: Double) {
        addTransactionRepository.save(value)
    }
}
