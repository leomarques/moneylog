package lmm.moneylog.data.account.repositories

interface ArchiveAccountRepository {
    suspend fun updateArchived(id: Int, archived: Boolean)
}
