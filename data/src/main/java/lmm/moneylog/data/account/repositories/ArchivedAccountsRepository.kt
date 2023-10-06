package lmm.moneylog.data.account.repositories

interface ArchivedAccountsRepository {
    suspend fun archive(id: Int)
    suspend fun unarchive(id: Int)
}
