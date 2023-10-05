package lmm.moneylog.data.account.repositories

interface ArchiveAccountRepository {
    suspend fun archive(id: Int)
}
