package lmm.moneylog.domain.deleteaccount

interface DeleteAccountRepository {
    suspend fun delete(id: Int)
}
