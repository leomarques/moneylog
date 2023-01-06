package lmm.moneylog.data

import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun save(value: Double)
    fun get(): Flow<List<TransactionEntity>>
}
