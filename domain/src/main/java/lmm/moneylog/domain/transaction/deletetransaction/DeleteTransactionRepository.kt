package lmm.moneylog.domain.transaction.deletetransaction

interface DeleteTransactionRepository {
    suspend fun delete(id: Int)
}
