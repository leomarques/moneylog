package lmm.moneylog.ui.features.account.archive.view.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.account.archive.model.ArchivedAccountModel
import lmm.moneylog.ui.features.account.detail.view.components.DeleteAccountConfirmDialog
import lmm.moneylog.ui.theme.Size

@Composable
fun ArchivedAccountsContent(
    list: List<ArchivedAccountModel>,
    onUnArchive: (Int) -> Unit,
    onDeleteConfirm: (Int) -> Unit
) {
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var idToDelete by remember { mutableIntStateOf(-1) }

    if (showDeleteConfirmDialog) {
        DeleteAccountConfirmDialog(
            onConfirm = {
                showDeleteConfirmDialog = false
                onDeleteConfirm(idToDelete)
            },
            onDismiss = { showDeleteConfirmDialog = false }
        )
    }

    Column(Modifier.fillMaxWidth()) {
        LazyColumn(
            Modifier.background(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(Size.ListRoundedCornerSize)
            )
        ) {
            itemsIndexed(list) { index, accountModel ->
                ArchivedAccountItem(
                    id = accountModel.id,
                    name = accountModel.name,
                    showDivider = index != list.size - 1,
                    onUnArchiveClick = onUnArchive,
                    onDeleteClick = { id ->
                        idToDelete = id
                        showDeleteConfirmDialog = true
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ArchivedAccountsContentPreview() {
    ArchivedAccountsContent(
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
        onDeleteConfirm = {}
    )
}
