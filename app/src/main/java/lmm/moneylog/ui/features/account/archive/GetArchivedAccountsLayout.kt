package lmm.moneylog.ui.features.account.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.TextPicker
import lmm.moneylog.ui.features.account.accountdetail.DeleteAccountConfirmDialog
import lmm.moneylog.ui.theme.SpaceSize

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
                    Text(text = stringResource(id = R.string.getarchivedaccounts_topbar))
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detailtransaction_arrowback_desc)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Surface(Modifier.padding(top = paddingValues.calculateTopPadding())) {
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
                        onDeleteConfirm(idToDelete)
                        showDeleteConfirmDialog = false
                    },
                    onDismissConfirmDialog = {
                        showDeleteConfirmDialog = false
                    },
                    showDeleteConfirmDialog = showDeleteConfirmDialog
                )
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

@Composable
fun ArchivedAccountItem(
    id: Int,
    name: String,
    onItemClick: (Int) -> Unit,
    onUnArchiveClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    showDivider: Boolean
) {
    var showMore by remember { mutableStateOf(false) }

    if (showMore) {
        TextPicker(
            list = listOf(
                stringResource(R.string.unarchive),
                stringResource(R.string.delete)
            ),
            onConfirm = { index ->
                when (index) {
                    0 -> {
                        onUnArchiveClick(id)
                    }

                    1 -> {
                        onDeleteClick(id)
                    }
                }
                showMore = false
            },
            onDismiss = {
                showMore = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SpaceSize.OneLineListItemHeight)
            .padding(
                vertical = SpaceSize.SmallSpaceSize,
                horizontal = SpaceSize.DefaultSpaceSize
            )
            .clickable { onItemClick(id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name.ifEmpty {
                stringResource(R.string.gettransactions_nodescription)
            },
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
            color = if (name.isEmpty()) {
                Color.Gray
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )

        IconButton(onClick = { showMore = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.more_options_desc)
            )
        }
    }

    if (showDivider) {
        Divider(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize))
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
