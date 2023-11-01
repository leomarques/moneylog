package lmm.moneylog.ui.features.account.archive.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.features.account.archive.model.ArchivedAccountModel
import lmm.moneylog.ui.features.account.archive.view.components.ArchivedAccountsTopBar

@Composable
fun ArchivedAccountsLayout(
    list: List<ArchivedAccountModel>,
    onArrowBackClick: () -> Unit,
    onUnArchive: (Int) -> Unit,
    onDeleteConfirm: (Int) -> Unit
) {
    Scaffold(
        topBar = { ArchivedAccountsTopBar(onArrowBackClick) },
        content = { paddingValues ->
            Surface(Modifier.padding(top = paddingValues.calculateTopPadding())) {
                if (list.isEmpty()) {
                    EmptyState(
                        title = stringResource(R.string.empty_archived_accounts_title),
                        description = stringResource(R.string.empty_archived_accounts_desc)
                    )
                } else {
                    var showDeleteConfirmDialog by remember {
                        mutableStateOf(false)
                    }

                    var idToDelete by remember {
                        mutableIntStateOf(-1)
                    }

                    ArchivedAccountsContent(
                        onItemClick = {},
                        list = list,
                        onUnArchive = onUnArchive,
                        onDeleteClick = { id ->
                            idToDelete = id
                            showDeleteConfirmDialog = true
                        },
                        onDeleteConfirm = {
                            showDeleteConfirmDialog = false
                            onDeleteConfirm(idToDelete)
                        },
                        onDismissConfirmDialog = {
                            showDeleteConfirmDialog = false
                        },
                        showDeleteConfirmDialog = showDeleteConfirmDialog
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun ArchivedAccountsLayoutPreview() {
    ArchivedAccountsLayout(
        onArrowBackClick = { },
        list = listOf(
            ArchivedAccountModel(
                id = 0,
                name = "Itaú"
            ),
            ArchivedAccountModel(
                id = 0,
                name = "Itaú"
            ),
            ArchivedAccountModel(
                id = 0,
                name = "Itaú"
            )
        ),
        onDeleteConfirm = {},
        onUnArchive = {}
    )
}
