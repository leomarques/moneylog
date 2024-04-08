package lmm.moneylog.ui.features.account.archive.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier,
    onDeleteConfirm: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            ArchivedAccountsTopBar(
                modifier = modifier,
                onArrowBackClick = onArrowBackClick
            )
        },
        content = { paddingValues ->
            Surface(Modifier.padding(top = paddingValues.calculateTopPadding())) {
                if (list.isEmpty()) {
                    EmptyState(
                        title = stringResource(R.string.empty_archived_accounts_title),
                        description = stringResource(R.string.empty_archived_accounts_desc)
                    )
                } else {
                    ArchivedAccountsContent(
                        list = list,
                        onUnArchive = onUnArchive,
                        onDeleteConfirm = onDeleteConfirm
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
        list =
            listOf(
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
