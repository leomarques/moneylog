package lmm.moneylog.ui.features.transaction.list.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.misc.SearchTopBar
import lmm.moneylog.ui.features.transaction.list.model.TransactionsListUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsListLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: TransactionsListUIState,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    val showTopBar = remember { mutableStateOf(true) }
    val filter = remember { mutableStateOf("") }
    var showFab by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (showTopBar.value) {
                TopAppBar(
                    title = {
                        Text(text = stringResource(model.titleResourceId))
                    },
                    navigationIcon = {
                        IconButton(onClick = onArrowBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.arrowback_desc)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { showTopBar.value = false },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.delete)
                                )
                            }
                        )
                    }
                )
            } else {
                SearchTopBar(
                    searchText = filter.value,
                    placeholderText = stringResource(R.string.search_placeholder),
                    onSearchTextChanged = { filter.value = it },
                    onClearClick = {
                        filter.value = ""
                        showTopBar.value = true
                    },
                    onArrowBackClick = onArrowBackClick
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (showFab) {
                MyFab(
                    onClick = {
                        showFab = false
                        onFabClick()
                    },
                    icon = Icons.Default.Add
                )
            }
        },
        content = { paddingValues ->
            Surface(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .testTag("TransactionsListScreen")
            ) {
                if (model.transactions.isNotEmpty()) {
                    TransactionsListContent(
                        list = model.transactions,
                        filter = filter.value,
                        onItemClick = onItemClick
                    )
                } else {
                    EmptyState(
                        stringResource(R.string.empty_transactions_title),
                        stringResource(R.string.empty_transactions_desc)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun TransactionsListLayoutPreview() {
    TransactionsListLayout(
        onArrowBackClick = {},
        onFabClick = {},
        model =
            TransactionsListUIState(
                titleResourceId = R.string.transactions,
                transactions = transactionModelListPreview
            ),
        onItemClick = {}
    )
}
