package lmm.moneylog.ui.features.account.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.EmptyState
import lmm.moneylog.ui.features.account.accountdetail.DeleteAccountConfirmDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetArchivedAccountsLayout(
    onArrowBackClick: () -> Unit,
    list: List<ArchivedAccountModel>,
    onUnArchive: (Int) -> Unit,
    onDeleteConfirm: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.topbar_archived_accounts))
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.arrowback_desc)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Surface(Modifier.padding(top = paddingValues.calculateTopPadding())) {
                if (list.isEmpty()) {
                    EmptyState(
                        stringResource(R.string.empty_archived_accounts_title),
                        stringResource(R.string.empty_archived_accounts_desc)
                    )
                } else {
                    var showDeleteConfirmDialog by remember {
                        mutableStateOf(false)
                    }

                    var idToDelete by remember {
                        mutableIntStateOf(-1)
                    }

                    GetArchivedAccountsContent(
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

@Composable
fun GetArchivedAccountsContent(
    onItemClick: (Int) -> Unit,
    list: List<ArchivedAccountModel>,
    onUnArchive: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDismissConfirmDialog: () -> Unit,
    showDeleteConfirmDialog: Boolean
) {
    if (showDeleteConfirmDialog) {
        DeleteAccountConfirmDialog(
            onConfirm = onDeleteConfirm,
            onDismiss = onDismissConfirmDialog
        )
    }

    Column(Modifier.fillMaxWidth()) {
        LazyColumn(
            Modifier.background(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(20.dp)
            )
        ) {
            itemsIndexed(list.reversed()) { index, accountModel ->
                ArchivedAccountItem(
                    id = accountModel.id,
                    name = accountModel.name,
                    onItemClick = onItemClick,
                    onUnArchiveClick = onUnArchive,
                    onDeleteClick = { id ->
                        onDeleteClick(id)
                    },
                    showDivider = index != list.size - 1
                )
            }
        }
    }
}

@Preview
@Composable
fun GetArchivedAccountsLayoutPreview() {
    GetArchivedAccountsLayout(
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
