package lmm.moneylog.ui.features.transaction.gettransactions

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.EmptyState
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.SearchTopBar

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun GetTransactionsLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: GetTransactionsModel,
    onItemClick: (Int) -> Unit
) {
    val showTopBar = remember { mutableStateOf(true) }
    val filter = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            if (showTopBar.value) {
                TopAppBar(
                    title = {
                        Text(text = stringResource(model.titleResourceId))
                    },
                    navigationIcon = {
                        IconButton(onClick = onArrowBackClick) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.detailtransaction_arrowback_desc)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { showTopBar.value = false },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.detailtransaction_delete_desc)
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
            MyFab(
                onClick = onFabClick,
                icon = Icons.Default.Add
            )
        },
        content = { paddingValues ->
            Surface(
                Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                if (model.transactions.isNotEmpty()) {
                    GetTransactionsList(
                        list = model.transactions,
                        filter = filter,
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
fun Preview() {
    GetTransactionsLayout(
        onArrowBackClick = {},
        onFabClick = {},
        model = GetTransactionsModel(
            listOf(
                TransactionModel(
                    value = "R$50,00",
                    isIncome = true,
                    description = "Calça",
                    date = "1/2/2023",
                    id = 0,
                    account = "Nubank",
                    category = "Roupa",
                    categoryColor = Color.Red
                ),
                TransactionModel(
                    value = "R$1,00",
                    isIncome = false,
                    description = "",
                    date = "22/12/2023",
                    id = 0,
                    account = "Nubank",
                    category = "Roupa",
                    categoryColor = Color.Red
                )
            ),
            R.string.gettransactions_topbar_all
        ),
        onItemClick = {}
    )
}
