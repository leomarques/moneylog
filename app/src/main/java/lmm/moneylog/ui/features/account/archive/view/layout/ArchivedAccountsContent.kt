package lmm.moneylog.ui.features.account.archive.view.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.ui.features.account.archive.model.ArchivedAccountModel
import lmm.moneylog.ui.features.account.detail.view.components.DeleteAccountConfirmDialog

@Composable
fun ArchivedAccountsContent(
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
fun ArchivedAccountsContentPreview() {
    ArchivedAccountsContent(
        onItemClick = {},
        list = listOf(
            ArchivedAccountModel(
                id = 1,
                name = "Account 1"
            ),
            ArchivedAccountModel(
                id = 2,
                name = "Account 2"
            ),
            ArchivedAccountModel(
                id = 3,
                name = "Account 3"
            )
        ),
        onUnArchive = {},
        onDeleteClick = {},
        onDeleteConfirm = {},
        onDismissConfirmDialog = {},
        showDeleteConfirmDialog = false
    )
}
