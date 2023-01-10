package lmm.moneylog.domain.addtransaction

interface AddTransactionRepository {
    suspend fun save(value: Double)
}