package lmm.moneylog.ui.features.settings

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.features.settings.viewmodel.BackupViewModel
import org.koin.androidx.compose.koinViewModel

@Suppress("CyclomaticComplexMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupScreen(
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    backupViewModel: BackupViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val exportState by backupViewModel.exportState.collectAsState()
    val importState by backupViewModel.importState.collectAsState()
    val showWipeConfirmDialog by backupViewModel.showWipeConfirmDialog.collectAsState()

    val exportLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    backupViewModel.exportData(uri, context.contentResolver)
                }
            }
        }

    val importLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    backupViewModel.showWipeConfirmationDialog(uri)
                }
            }
        }

    val backupExportSuccessTemplate = stringResource(R.string.backup_export_success)
    val backupExportErrorTemplate = stringResource(R.string.backup_export_error)
    val backupImportSuccessTemplate = stringResource(R.string.backup_import_success)
    val backupImportErrorTemplate = stringResource(R.string.backup_import_error)

    LaunchedEffect(exportState) {
        when (val state = exportState) {
            is BackupViewModel.ExportState.Success -> {
                snackbarHostState.showSnackbar(
                    String.format(
                        backupExportSuccessTemplate,
                        state.accountsCount,
                        state.categoriesCount,
                        state.creditCardsCount,
                        state.transactionsCount
                    )
                )
                backupViewModel.clearExportState()
            }
            is BackupViewModel.ExportState.Error -> {
                snackbarHostState.showSnackbar(String.format(backupExportErrorTemplate, state.message))
                backupViewModel.clearExportState()
            }
            else -> Unit
        }
    }

    LaunchedEffect(importState) {
        when (val state = importState) {
            is BackupViewModel.ImportState.Success -> {
                snackbarHostState.showSnackbar(
                    String.format(
                        backupImportSuccessTemplate,
                        state.accountsImported,
                        state.categoriesImported,
                        state.creditCardsImported,
                        state.transactionsImported,
                        state.accountTransfersImported,
                        state.categoryKeywordsImported
                    )
                )
                backupViewModel.clearImportState()
            }
            is BackupViewModel.ImportState.Error -> {
                snackbarHostState.showSnackbar(String.format(backupImportErrorTemplate, state.message))
                backupViewModel.clearImportState()
            }
            else -> Unit
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_backup_data)) },
                windowInsets = WindowInsets(0.dp),
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val isExportLoading = exportState is BackupViewModel.ExportState.Loading
        val isImportLoading = importState is BackupViewModel.ImportState.Loading

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column {
                    BackupActionItem(
                        title = stringResource(R.string.settings_backup_export),
                        description = stringResource(R.string.settings_backup_export_desc),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_backup_24),
                        onClick = {
                            exportLauncher.launch(
                                Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                                    addCategory(Intent.CATEGORY_OPENABLE)
                                    type = "text/csv"
                                    putExtra(Intent.EXTRA_TITLE, "lemony_backup.csv")
                                }
                            )
                        },
                        enabled = !isExportLoading && !isImportLoading,
                        isLoading = isExportLoading
                    )

                    HorizontalDivider()

                    BackupActionItem(
                        title = stringResource(R.string.settings_backup_import),
                        description = stringResource(R.string.settings_backup_import_desc),
                        icon = ImageVector.vectorResource(id = R.drawable.outline_restore_24),
                        onClick = {
                            importLauncher.launch(
                                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                    addCategory(Intent.CATEGORY_OPENABLE)
                                    type = "*/*"
                                    putExtra(
                                        Intent.EXTRA_MIME_TYPES,
                                        arrayOf(
                                            "text/csv",
                                            "text/plain",
                                            "text/comma-separated-values",
                                            "application/csv"
                                        )
                                    )
                                }
                            )
                        },
                        enabled = !isExportLoading && !isImportLoading,
                        isLoading = isImportLoading
                    )
                }
            }
        }

        if (showWipeConfirmDialog) {
            AlertDialog(
                onDismissRequest = { backupViewModel.dismissWipeConfirmationDialog() },
                title = { Text(stringResource(R.string.backup_import_dialog_title)) },
                text = { Text(stringResource(R.string.backup_import_dialog_message)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            backupViewModel.confirmWipeAndImport(true, context.contentResolver)
                        }
                    ) {
                        Text(stringResource(R.string.backup_import_replace))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            backupViewModel.confirmWipeAndImport(false, context.contentResolver)
                        }
                    ) {
                        Text(stringResource(R.string.backup_import_merge))
                    }
                }
            )
        }
    }
}

@Composable
private fun BackupActionItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    androidx.compose.foundation.layout.Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(enabled = enabled && !isLoading, onClick = onClick)
                .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(4.dp),
                strokeWidth = 2.dp
            )
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint =
                    if (enabled) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    }
            )
        }
    }
}
