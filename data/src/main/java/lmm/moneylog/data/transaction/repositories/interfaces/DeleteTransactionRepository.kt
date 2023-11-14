package lmm.moneylog.data.transaction.repositories.interfaces

interface DeleteTransactionRepository {
    suspend fun delete(id: Int)
}
