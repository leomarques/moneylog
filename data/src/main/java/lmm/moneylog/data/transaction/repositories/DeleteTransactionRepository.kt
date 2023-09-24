package lmm.moneylog.data.transaction.repositories

interface DeleteTransactionRepository {
    suspend fun delete(id: Int)
}
