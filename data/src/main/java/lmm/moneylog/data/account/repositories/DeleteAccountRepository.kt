package lmm.moneylog.data.account.repositories

interface DeleteAccountRepository {
    suspend fun delete(id: Int)
}
