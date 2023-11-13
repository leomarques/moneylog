package lmm.moneylog.data.account.repositories.interfaces

interface ArchiveAccountRepository {
    suspend fun updateArchived(
        id: Int,
        archived: Boolean
    )
}
