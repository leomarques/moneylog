package lmm.moneylog.data.account.repositories.interfaces

interface DeleteAccountRepository {
    suspend fun delete(id: Int)
}
