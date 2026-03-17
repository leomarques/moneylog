package lmm.moneylog.ui.features.settings.viewmodel

import android.net.Uri
import java.io.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lmm.moneylog.data.backup.repositories.interfaces.BackupRepository
import lmm.moneylog.data.backup.repositories.interfaces.ImportResult
import lmm.moneylog.data.backup.utils.CsvBackupUtil

class BackupViewModel(
    private val backupRepository: BackupRepository
) : ViewModel() {

    private val csvBackupUtil = CsvBackupUtil()

    private val _exportState = MutableStateFlow<ExportState>(ExportState.Idle)
    val exportState: StateFlow<ExportState> = _exportState.asStateFlow()

    private val _importState = MutableStateFlow<ImportState>(ImportState.Idle)
    val importState: StateFlow<ImportState> = _importState.asStateFlow()

    private val _showWipeConfirmDialog = MutableStateFlow(false)
    val showWipeConfirmDialog: StateFlow<Boolean> = _showWipeConfirmDialog.asStateFlow()

    private var pendingImportUri: Uri? = null

    fun showWipeConfirmationDialog(uri: Uri) {
        pendingImportUri = uri
        _showWipeConfirmDialog.value = true
    }

    fun dismissWipeConfirmationDialog() {
        _showWipeConfirmDialog.value = false
        pendingImportUri = null
    }

    fun confirmWipeAndImport(wipeData: Boolean, contentResolver: android.content.ContentResolver) {
        val uri = pendingImportUri ?: return
        _showWipeConfirmDialog.value = false
        importData(uri, contentResolver, wipeData)
        pendingImportUri = null
    }

    fun exportData(uri: Uri, contentResolver: android.content.ContentResolver) {
        viewModelScope.launch {
            _exportState.value = ExportState.Loading
            try {
                val backupData = backupRepository.exportAllData()
                val csvContent = csvBackupUtil.exportToCsv(backupData)

                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(csvContent.toByteArray(Charsets.UTF_8))
                }

                _exportState.value = ExportState.Success(
                    accountsCount = backupData.accounts.size,
                    categoriesCount = backupData.categories.size,
                    creditCardsCount = backupData.creditCards.size,
                    transactionsCount = backupData.transactions.size
                )
            } catch (e: IOException) {
                _exportState.value = ExportState.Error(e.message ?: "Export failed")
            } catch (e: IllegalStateException) {
                _exportState.value = ExportState.Error(e.message ?: "Export failed")
            }
        }
    }

    fun importData(
        uri: Uri,
        contentResolver: android.content.ContentResolver,
        wipeBeforeImport: Boolean = false
    ) {
        viewModelScope.launch {
            _importState.value = ImportState.Loading
            try {
                val csvContent = contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.bufferedReader().use { it.readText() }
                } ?: throw IllegalStateException("Could not read file")

                val backupData = csvBackupUtil.importFromCsv(csvContent)
                val result = backupRepository.importAllData(backupData, wipeBeforeImport)

                when (result) {
                    is ImportResult.Success -> {
                        _importState.value = ImportState.Success(
                            accountsImported = result.accountsImported,
                            categoriesImported = result.categoriesImported,
                            creditCardsImported = result.creditCardsImported,
                            transactionsImported = result.transactionsImported,
                            accountTransfersImported = result.accountTransfersImported,
                            categoryKeywordsImported = result.categoryKeywordsImported
                        )
                    }
                    is ImportResult.Error -> {
                        _importState.value = ImportState.Error(result.message)
                    }
                }
            } catch (e: IOException) {
                _importState.value = ImportState.Error(e.message ?: "Import failed")
            } catch (e: IllegalStateException) {
                _importState.value = ImportState.Error(e.message ?: "Import failed")
            }
        }
    }

    fun clearExportState() {
        _exportState.value = ExportState.Idle
    }

    fun clearImportState() {
        _importState.value = ImportState.Idle
    }

    sealed class ExportState {
        data object Idle : ExportState()
        data object Loading : ExportState()
        data class Success(
            val accountsCount: Int,
            val categoriesCount: Int,
            val creditCardsCount: Int,
            val transactionsCount: Int
        ) : ExportState()
        data class Error(val message: String) : ExportState()
    }

    sealed class ImportState {
        data object Idle : ImportState()
        data object Loading : ImportState()
        data class Success(
            val accountsImported: Int,
            val categoriesImported: Int,
            val creditCardsImported: Int,
            val transactionsImported: Int,
            val accountTransfersImported: Int,
            val categoryKeywordsImported: Int
        ) : ImportState()
        data class Error(val message: String) : ImportState()
    }
}
