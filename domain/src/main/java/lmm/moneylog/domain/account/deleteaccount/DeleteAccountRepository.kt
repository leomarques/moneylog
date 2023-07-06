package lmm.moneylog.domain.account.deleteaccount

interface DeleteAccountRepository {
    suspend fun delete(id: Int)
}
