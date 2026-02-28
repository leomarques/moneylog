package lmm.moneylog.ui.features.transfer.list.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.misc.MonthSelector
import lmm.moneylog.ui.components.topappbar.TopAppBarWithSearch
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.features.transfer.list.model.TransfersListUIState
import lmm.moneylog.ui.features.transfer.list.model.filtered
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun TransfersListLayout(
    model: TransfersListUIState,
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var filter by remember { mutableStateOf("") }
    var showFab by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBarWithSearch(
                onSearchTextChange = { filter = it },
                onArrowBackClick = onArrowBackClick,
                filter = filter,
                titleResourceId = model.titleResourceId
            )
        },
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
            Column(
                Modifier
                    .padding(paddingValues = paddingValues)
                    .testTag("TransfersListScreen")
            ) {
                MonthSelector(
                    onPreviousMonthClick = onPreviousMonthClick,
                    onNextMonthClick = onNextMonthClick,
                    monthName = model.monthName.replaceFirstChar { it.uppercase() },
                    modifier = Modifier.fillMaxWidth()
                )

                TotalValue(model)

                List(
                    model = model,
                    filter = filter,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Composable
private fun TotalValue(model: TransfersListUIState) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.common_total, model.total.formatForRs()),
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            color = neutralColor
        )
    }
}

@Composable
private fun List(
    model: TransfersListUIState,
    filter: String,
    onItemClick: (Int) -> Unit
) {
    Box {
        if (model.transfers.isNotEmpty()) {
            TransfersListContent(
                list = model.transfers.filtered(filter),
                onItemClick = onItemClick
            )
        } else {
            EmptyState(
                stringResource(R.string.empty_transfers_title),
                stringResource(R.string.empty_transfers_description)
            )
        }
    }
}

@Preview
@Composable
private fun TransfersListLayoutPreview() {
    TransfersListLayout(
        onArrowBackClick = {},
        onFabClick = {},
        onItemClick = {},
        model =
            TransfersListUIState(
                titleResourceId = R.string.transfer_list_title,
                monthName = "January",
                transfers = transferModelListPreview,
                total = 500.0
            ),
        onPreviousMonthClick = {},
        onNextMonthClick = {}
    )
}
