package lmm.moneylog.domain.deletetransaction

interface DeleteTransactionRepository {
    suspend fun delete(id: Int)
}
