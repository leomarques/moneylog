package lmm.moneylog.data

interface TransactionRepository {
    suspend fun save(value: Double)
}
