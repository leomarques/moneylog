package lmm.moneylog.data.backup.repositories.interfaces

import lmm.moneylog.data.backup.model.BackupData

interface BackupRepository {
    suspend fun exportAllData(): BackupData

    suspend fun importAllData(data: BackupData): ImportResult

    suspend fun importAllData(data: BackupData, wipeBeforeImport: Boolean): ImportResult

    suspend fun clearAllData()
}

sealed class ImportResult {
    data class Success(
        val accountsImported: Int,
        val categoriesImported: Int,
        val creditCardsImported: Int,
        val transactionsImported: Int,
        val accountTransfersImported: Int,
        val categoryKeywordsImported: Int
    ) : ImportResult()

    data class Error(
        val message: String
    ) : ImportResult()
}
