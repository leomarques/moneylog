package lmm.moneylog.data

import androidx.lifecycle.LiveData

interface TransactionRepository {
    suspend fun save(value: Double)
    fun get(): LiveData<List<TransactionEntity>>
}
